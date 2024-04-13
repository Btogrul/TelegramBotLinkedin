package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.TelegramBotClient;
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
    private final TelegramBotClient bot;
    private final UserRepository userRepo;
    private final SkillRepository skillRepo;
    private final ModelMapper mpr;
    private long lastUpdateId;
    private final ArrayDeque<UserRequestDTO> queue = new ArrayDeque<>();
    private final Set<Long> welcomedUsers = new HashSet<>();


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
     * Sets stages of the users based on request and distributes them to related methods.
     */
    public void stageRequests() {
        while (!queue.isEmpty()) {
            UserRequestDTO request = queue.poll();
            long chatId = request.getChatId();
            UserOfBot user = userRepo.findUser(Math.toIntExact(chatId));
            String text = request.getText();

            log.info("request for chatId: {}, text: {}", chatId, text);
            switch (text) {
                case "/start" -> createUser(request, user);
                case "/new" -> newJobSearch(user);
                case "/edit" -> editJobSearch(user);
                case "/delete" -> deleteUser(user);
                default -> processRequests(request, user);
            }
        }
    }

    public void createUser(UserRequestDTO request, UserOfBot user) {
        long chatId = request.getChatId();
        if (user == null) {
            user = mpr.map(request, UserOfBot.class);
            user.setStage(UserStage.CREATED);
            user = userRepo.save(user);
            log.info("Saved user: " + user);
        } else {
            log.info("User %d exists".formatted(user.getChatId()));
        }
        if (!welcomedUsers.contains(chatId)) {
            log.info("Sending welcome message to chatId: {}", chatId);
            bot.sendMessage(chatId, "Welcome to the Bizim Mehle.");
            welcomedUsers.add(chatId);
        }
    }

    public void newJobSearch(UserOfBot user) {
        log.info("User is entering job title");
        if (user!=null && user.getJobTitle() == null) {
            user.setStage(UserStage.ENTERING_JOB);
            userRepo.save(user);
            log.info("User stage: " + user.getStage());
        }
    }

    public void editJobSearch (UserOfBot user) {
        log.info("User is editing details");
        if (user!=null) {
            user.setStage(UserStage.ENTERING_JOB);
            userRepo.save(user);
            log.info(user.toString());
        }
    }


    public void processRequests(UserRequestDTO request, UserOfBot user) {
        UserStage stage = user == null ? null : user.getStage();
        switch (stage) {
            case CREATED -> log.info("Enter /new to start");
            case ENTERING_JOB -> jobTitleSetter (request, user);
            case ENTERING_SKILLS -> skillSetSetter(request, user);
            case PROCESSED -> log.info("All set!");
            case null -> log.info("Wrong input!");
        }
    }

    public void jobTitleSetter (UserRequestDTO request, UserOfBot user) {
            user.setJobTitle(request.getText());
            user.setStage(UserStage.ENTERING_SKILLS);
            user = userRepo.save(user);
            log.info(user.toString());
    }

    public void skillSetSetter(UserRequestDTO request, UserOfBot user) {
        String text = request.getText();
        Set<String> skills = Set.of(text.split("\\s*,\\s*"));
        log.info("String list: " + skills);

        List<Skill> skillSet = findOrCreateSkills(skills);
        log.info("Skill set: " + skillSet);

        user.setSkillSet(skillSet);
        user.setStage(UserStage.PROCESSED);
        user = userRepo.save(user);
        log.info(user.toString());
    }

    public List<Skill> findOrCreateSkills (Set<String> skills) {
        List<Skill> skillSet = new ArrayList<>();
        for (String s : skills) {
            log.info(s);
            Skill skill = skillRepo.findSkill(s);
            if (skill == null) {
                skill = mpr.map(s, Skill.class);
                skill = skillRepo.save(skill);
                log.info("skill: " + skill);
            }
            skillSet.add(skill);
        }
        return skillSet;
    }

    public void deleteUser(UserOfBot user) {
        if (user != null) userRepo.delete(user);
    }
}
