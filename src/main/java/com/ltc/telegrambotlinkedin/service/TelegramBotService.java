package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.TelegramBotClient;
import com.ltc.telegrambotlinkedin.dto.jSearchDto.Datum;
import com.ltc.telegrambotlinkedin.dto.jSearchDto.JSearchRoot;
import com.ltc.telegrambotlinkedin.dto.telegramBot.request.BotUpdatesDTO;
import com.ltc.telegrambotlinkedin.dto.telegramBot.request.Result;
import com.ltc.telegrambotlinkedin.dto.userDTO.UserRequestDTO;
import com.ltc.telegrambotlinkedin.entity.Skill;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import com.ltc.telegrambotlinkedin.enums.UserStage;
import com.ltc.telegrambotlinkedin.repository.SkillRepository;
import com.ltc.telegrambotlinkedin.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Data
@Service
@RequiredArgsConstructor
public class TelegramBotService {

    private final UserRepository userRepo;
    private final SkillRepository skillRepo;

    private final ModelMapper mpr;
    private final TelegramBotClient bot;
    private final JSearchService jSearchService;

    private long lastUpdateId;
    private final ArrayDeque<UserRequestDTO> queue = new ArrayDeque<>();
    private Set<String> jobsSentToUser = new HashSet<>();

    /**
     * Gets raw updates starting from the last update id.
     * If any update presents maps them into UserRequestDTO and adds them into queue.
     * Starts the next stage of processing the requests.
     */
    public void getUpdates() {
        BotUpdatesDTO updates = bot.getUpdates(lastUpdateId+1);
        ArrayList<Result> results = updates.getResults();
        if (!results.isEmpty()) {
            lastUpdateId = results.getLast().getUpdate_id();
            ArrayList<UserRequestDTO> requests = new ArrayList<>();
            for (Result r : results) {
                UserRequestDTO requestDTO = mpr.map(r, UserRequestDTO.class);
                mpr.map(r.getMessage(), requestDTO);
                mpr.map(r.getMessage().getChat(), requestDTO);
                requests.add(requestDTO);
            }
            queue.addAll(requests);
            stageRequests();
        }
    }

    /**
     * Sets stages of the users based on commands.
     * If message is not command processes requests.
     * At the end distributes them to related methods.
     */
    public void stageRequests() {
        while (!queue.isEmpty()) {
            UserRequestDTO request = queue.poll();
            long chatId = request.getChatId();
            UserOfBot user = userRepo.findUser(Math.toIntExact(chatId));
            String text = request.getText();

            switch (text) {
                case "/start" -> createUser(request, user);
                case "/new", "/edit" -> newJobSearch(chatId, user);
                case "/delete" -> deleteUser(user);
                default -> processRequests(request, user);
            }
        }
    }

    /**
     * Method works if user sent /start command for the first time or deleted all records previously.
     * @param request - contains all useful data about the user.
     * @param user - is the queried user from database provided by stageRequests() method. If user is new it will be null.
     */
    public void createUser(UserRequestDTO request, UserOfBot user) {
        long chatId = request.getChatId();
        boolean isNew = false;
        if (user == null) {
            user = mpr.map(request, UserOfBot.class);
            user.setStage(UserStage.CREATED);
            user = userRepo.save(user);
            isNew = true;
        }

        if (isNew) {
            bot.sendMessage(chatId, "Hello %s 🤩. You are in the right place 🤖👉👉".formatted(user.getFirstName()));
            bot.sendMessage(chatId, "Enter the /new command to start a new job search.");
        } else {
            bot.sendMessage(chatId, """
                    To start a new job search, choose the /new command.
                    If you need to edit your current job search, choose /edit command.""");
        }
    }

    /**
     * The method works if user sent /new or /edit commands.
     * The method sets the user stage to ENTERING_JOB if user is already in database.
     * This method helps program to know that the next request is going to be a job title.
     * @param user - contains all useful data about the user.
     */
    public void newJobSearch(long chatId, UserOfBot user) {
        if (user!=null) {
            user.setStage(UserStage.ENTERING_JOB);
            userRepo.save(user);
            bot.sendMessage(user.getChatId(), "1/3 Enter the title of the job you want to apply to.");
        } else {
            bot.sendMessage(chatId, "To start a new job search, choose the /new command.");
        }
    }

    /**
     * This method processes requests based on the stage of the user.
     * @param request - contains all useful data about the user.
     * @param user - is the queried user from database provided by stageRequests() method. If user is new it will be null.
     */
    public void processRequests(UserRequestDTO request, UserOfBot user) {
        UserStage stage = user == null ? null : user.getStage();
        switch (stage) {
            case CREATED -> bot.sendMessage(user.getChatId(), """
                    To start a new job search, choose the /new command.
                    If you need to edit your current job search, choose /edit command.""");
            case ENTERING_JOB -> jobTitleSetter (request, user);
            case ENTERING_SKILLS -> skillSetSetter(request, user);
            case ENTERING_LOCATION -> locationSetter(request, user);
            case CONFIRM_JOB_TITLE -> confirmJobTitleAndSearch(request, user);
            case PROCESSED -> log.info("All set!");
            case null -> bot.sendMessage(request.getChatId(), """
                    Enter /start to get started!""");
            default -> throw new IllegalStateException("Unexpected value: " + stage);
        }
    }

    /**
     * The method sets the title of the job and puts the user in ENTERING_SKILLS stage to process furthermore.
     * @param request - contains all useful data about the user.
     * @param user - is the queried user from database provided by stageRequests() method. If user is new it will be null.
     */
    public void jobTitleSetter (UserRequestDTO request, UserOfBot user) {
            user.setJobTitle(request.getText());
            user.setStage(UserStage.CONFIRM_JOB_TITLE);
            user.setStage(UserStage.ENTERING_SKILLS);
            user = userRepo.save(user);
            log.info(user.toString());
            log.info("Job title set to: {}", user.getJobTitle());
            userRepo.save(user);
            bot.sendMessage(user.getChatId(), """
                2/3 Enter your comma delimited skill set as in example:
                    this skill, that skill, another skill""");
    }

    /**
     * The method sets the skill set of the user and puts the user in ENTERING_LOCATION stage to process furthermore.
     * @param request - contains all useful data about the user.
     * @param user - is the queried user from database provided by stageRequests() method. If user is new it will be null.
     */
    public void skillSetSetter(UserRequestDTO request, UserOfBot user) {
        String text = request.getText();
        Set<String> skills = Set.of(text.split("\\s*,\\s*"));
        List<Skill> skillSet = findOrCreateSkills(skills);

        user.setSkillSet(skillSet);
        user.setStage(UserStage.ENTERING_LOCATION);
        userRepo.save(user);

        bot.sendMessage(user.getChatId(), """
                                3/3 Enter the location where you want to work. Example:
                                    Baku""");
    }

    /**
     * The method sets the location of the user and puts the user in PROCESSED stage to process furthermore.
     * @param request - contains all useful data about the user.
     * @param user - is the queried user from database provided by stageRequests() method. If user is new it will be null.
     */
    public void locationSetter (UserRequestDTO request, UserOfBot user) {
        user.setLocation(request.getText());
        user.setStage(UserStage.PROCESSED);
        userRepo.save(user);
        StringBuilder skills = new StringBuilder();
        user.getSkillSet().forEach(s -> skills.append(s.toString().indent(6)));
        bot.sendMessage(user.getChatId(), """
                All set 🤩
                1️⃣ Job title:
                %s
                2️⃣ Skill set:
                %s
                3️⃣ Location:
                %s""".formatted(user.getJobTitle().indent(6), skills, user.getLocation().indent(6)));
    }

    /**
     * This method searches in the database for skills provided by user. If it finds retrieves them or creates them.
     * @param skills - a set of Strings representing the skills that user entered.
     * @return - returns a List of Skill entities that are found or created.
     */
    public List<Skill> findOrCreateSkills (Set<String> skills) {
        List<Skill> skillSet = new ArrayList<>();
        for (String s : skills) {
            Skill skill = skillRepo.findSkill(s);
            if (skill == null) {
                skill = mpr.map(s, Skill.class);
                skill = skillRepo.save(skill);
            }
            skillSet.add(skill);
        }
        return skillSet;
    }

    /**
     * Deletes the record related to the user.
     * @param user - is the queried user from database provided by stageRequests() method. If user is new it will be null.
     */
    public void deleteUser(UserOfBot user) {
        if (user != null) userRepo.delete(user);
    }


    //-------------------------------

    public void confirmJobTitleAndSearch(UserRequestDTO request, UserOfBot user) {
        String jobTitle = request.getText();
        if (!Objects.equals(user.getJobTitle(), jobTitle)) {
            user.setJobTitle(jobTitle);
            user.setStage(UserStage.CONFIRM_JOB_TITLE);
            userRepo.save(user);
            bot.sendMessage(request.getChatId(), "confirm your job title: " + jobTitle);
        } else {
            searchJobs(user);
        }
    }


    public void searchJobs(UserOfBot user) {
        JSearchRoot jobs = jSearchService.getJobSearchResults(user.getJobTitle(), 1, 10);
        ArrayList<Datum> jobList = jobs.getData();
        if (jobList.isEmpty()) {
            bot.sendMessage(user.getChatId(), "jobs not found for the given title: " + user.getJobTitle());
            return;
        }

        Set<String> uniqueJobs = new HashSet<>();
        for (Datum job : jobList) {
            String jobString = job.getJob_title() + " (" + job.getEmployer_name() + ")";
            if (!uniqueJobs.contains(jobString)) {
                uniqueJobs.add(jobString);
                if (jobsSentToUser.add(job.getJob_id())) {
                    bot.sendMessage(user.getChatId(), "New job found: " + jobString);
                } else {
                    bot.sendMessage(user.getChatId(), "Job found again: " + jobString);
                }
            }
        }
    }
}
