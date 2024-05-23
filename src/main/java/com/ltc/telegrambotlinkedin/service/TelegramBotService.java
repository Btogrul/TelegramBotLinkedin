package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.TelegramBotClient;
import com.ltc.telegrambotlinkedin.dto.telegramBotDTO.BotUpdatesDTO;
import com.ltc.telegrambotlinkedin.dto.telegramBotDTO.Result;
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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Data
@Service
public class TelegramBotService {


    private final UserRepository userRepo;
    private final SkillRepository skillRepo;

    private final ModelMapper mpr;
    private final TelegramBotClient bot;

    private long lastUpdateId;
    private final ArrayDeque<UserRequestDTO> queue = new ArrayDeque<>();

    private final MessageSource messageSource;

    /**
     * Gets raw updates starting from the last update id.
     * If any update presents maps them into UserRequestDTO and adds them into queue.
     * Starts the next stage of processing the requests.
     */
    public void getUpdates() {
        BotUpdatesDTO updates = bot.getUpdates(lastUpdateId + 1);
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
            UserOfBot user = userRepo.findUser(chatId);
            String text = request.getText();

            switch (text) {
                case "/start" -> createUser(request, user);
                case "/new", "/edit" -> createJobSearch(chatId, user);
                case "/delete" -> deleteUser(user);
                case "/aze", "/rus", "/eng" -> setUserLanguage(request, user);
                case null -> bot.sendMessage(chatId, getLocalizedMessage("wrongMessage", user));
                default -> processRequests(request, user);
            }
        }
    }

    /**
     * Method works if user sent /start command for the first time or deleted all records previously.
     *
     * @param request - contains all useful data about the user.
     * @param user    - is the queried user from database provided by stageRequests() method. If user is new it will be null.
     */
    public void createUser(UserRequestDTO request, UserOfBot user) {
        long chatId = request.getChatId();
        if (user == null) {
            user = mpr.map(request, UserOfBot.class);
            user.setStage(UserStage.CREATED);
            userRepo.save(user);
            bot.sendMessage(chatId, """
                    Azərbaycan dili üçün /aze seçin.
                    Для русского языка выберите /rus.
                    For English language choose /eng""");
        } else {
            bot.sendMessage(chatId, getLocalizedMessage("enterNewCommand", user) +
                    "\n" + getLocalizedMessage("enterEditCommand", user));
        }
    }

    public void setUserLanguage(UserRequestDTO request, UserOfBot user) {
        long chatId = request.getChatId();
        if (user == null) {
            bot.sendMessage(chatId, getLocalizedMessage("enterStartCommand", null));
        } else {
            String lang = request.getText();
            user.setUserLocale(switch (lang) {
                case "/aze" -> Locale.forLanguageTag("az-Latn-AZ");
                case "/rus" -> Locale.forLanguageTag("ru-RU");
                case null, default -> Locale.ENGLISH;
            });
            userRepo.save(user);
            bot.sendMessage(chatId, getLocalizedMessage("languageIsSet", user));
            if (user.getStage() == UserStage.CREATED) {
                bot.sendMessage(chatId, getLocalizedMessage("sendGreeting", user).formatted(user.getFirstName()));
                bot.sendMessage(chatId, getLocalizedMessage("enterNewCommand", user));
            }
        }
    }

    public String getLocalizedMessage(String string, UserOfBot user) {
        Locale locale = user == null ? Locale.ENGLISH : user.getUserLocale();
        locale = locale == null ? Locale.ENGLISH : locale;
        return messageSource.getMessage(string, null, locale);
    }

    /**
     * The method works if user sent /new or /edit commands.
     * The method sets the user stage to ENTERING_JOB if user is already in database.
     * This method helps program to know that the next request is going to be a job title.
     *
     * @param user - contains all useful data about the user.
     */
    public void createJobSearch(long chatId, UserOfBot user) {
        if (user != null) {
            user.setStage(UserStage.ENTERING_TITLE);
            userRepo.save(user);
            bot.sendMessage(user.getChatId(), getLocalizedMessage("enterJobTitle", user));
        } else {
            bot.sendMessage(chatId, getLocalizedMessage("enterStartCommand", null));
        }
    }

    /**
     * This method processes requests based on the stage of the user.
     *
     * @param request - contains all useful data about the user.
     * @param user    - is the queried user from database provided by stageRequests() method. If user is new it will be null.
     */
    public void processRequests(UserRequestDTO request, UserOfBot user) {
        UserStage stage = user == null ? null : user.getStage();
        switch (stage) {
            case CREATED, PROCESSED -> bot.sendMessage(user.getChatId(),
                    getLocalizedMessage("enterNewCommand", user) + "\n" +
                            getLocalizedMessage("enterEditCommand", user));
            case ENTERING_TITLE -> jobTitleSetter(request, user);
            case ENTERING_SKILLS -> skillSetSetter(request, user);
            case ENTERING_REMOTE -> jobRemoteSetter(request,user);
            case ENTERING_LOCATION -> locationSetter(request, user);
            case CONFIRM_SEARCH -> confirmJobTitleAndSearch(request, user);
            case null -> bot.sendMessage(request.getChatId(), getLocalizedMessage("enterStartCommand", user));
        }
    }

    /**
     * The method sets the title of the job and puts the user in ENTERING_SKILLS stage to process furthermore.
     *
     * @param request - contains all useful data about the user.
     * @param user    - is the queried user from database provided by stageRequests() method. If user is new it will be null.
     */
    public void jobTitleSetter(UserRequestDTO request, UserOfBot user) {
        user.setJobTitle(request.getText().toLowerCase());
        user.setStage(UserStage.ENTERING_SKILLS);
        user = userRepo.save(user);
        userRepo.save(user);
        bot.sendMessage(user.getChatId(), getLocalizedMessage("enterSkills", user));
    }


    public void jobRemoteSetter(UserRequestDTO request, UserOfBot user){
        user.setRemoteJob(request.getText());
        user.setStage(UserStage.ENTERING_LOCATION);
        userRepo.save(user);
        bot.sendMessage(user.getChatId(), getLocalizedMessage("enterLocation", user));
    }

    /**
     * The method sets the skill set of the user and puts the user in ENTERING_LOCATION stage to process furthermore.
     *
     * @param request - contains all useful data about the user.
     * @param user    - is the queried user from database provided by stageRequests() method. If user is new it will be null.
     */
    public void skillSetSetter(UserRequestDTO request, UserOfBot user) {
        String text = request.getText().toLowerCase();
        Set<String> skills = Set.of(text.split("\\s*,\\s*"));
        List<Skill> skillSet = findOrCreateSkills(skills);

        user.setSkillSet(skillSet);
        user.setStage(UserStage.ENTERING_REMOTE);
        userRepo.save(user);

        bot.sendMessage(user.getChatId(), getLocalizedMessage("enterRemote", user));
    }

    /**
     * The method sets the location of the user and puts the user in CONFIRM_SEARCH stage to process furthermore.
     *
     * @param request - contains all useful data about the user.
     * @param user    - is the queried user from database provided by stageRequests() method. If user is new it will be null.
     */
    public void locationSetter(UserRequestDTO request, UserOfBot user) {
        String location = request.getText().substring(0, 1).toUpperCase() + request.getText().substring(1).toLowerCase();
        user.setUserLocation(location);
        user.setStage(UserStage.CONFIRM_SEARCH);
        userRepo.save(user);
        StringBuilder skills = new StringBuilder();
        user.getSkillSet().forEach(s -> skills.append(s.toString().indent(6)));
        bot.sendMessage(user.getChatId(), getLocalizedMessage("doubleCheck", user)
                .formatted(user.getJobTitle().indent(6), skills, user.getRemoteJob() , user.getUserLocation().indent(6)));
    }

    /**
     * The method checks if user passed double check successfully. If passed finishes the processing.
     * If not passed, asks user to try again or edit his/her details in case of wrong data found.
     *
     * @param request - contains all useful data about the user.
     * @param user    - is the queried user from database provided by stageRequests() method. If user is new it will be null.
     */
    public void confirmJobTitleAndSearch(UserRequestDTO request, UserOfBot user) {
        String jobTitle = request.getText();
        if (jobTitle.equalsIgnoreCase(user.getJobTitle())) {
            user.setStage(UserStage.PROCESSED);
            userRepo.save(user);
            bot.sendMessage(request.getChatId(), getLocalizedMessage("sendDone", user));
        } else {
            bot.sendMessage(request.getChatId(), getLocalizedMessage("wrongTitle", user));
        }
    }

    /**
     * This method searches in the database for skills provided by user. If it finds retrieves them or creates them.
     *
     * @param skills - a set of Strings representing the skills that user entered.
     * @return - returns a List of Skill entities that are found or created.
     */
    public List<Skill> findOrCreateSkills(Set<String> skills) {
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
     *
     * @param user - is the queried user from database provided by stageRequests() method. If user is new it will be null.
     */
    public void deleteUser(UserOfBot user) {
        if (user != null) userRepo.delete(user);
    }

}
