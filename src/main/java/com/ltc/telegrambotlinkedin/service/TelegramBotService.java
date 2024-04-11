package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.TelegramBotClient;
import com.ltc.telegrambotlinkedin.dto.telegramBot.request.BotUpdatesDTO;
import com.ltc.telegrambotlinkedin.dto.telegramBot.request.Result;
import com.ltc.telegrambotlinkedin.dto.userDTO.UserRequestDTO;
import com.ltc.telegrambotlinkedin.dto.userDTO.UserResponseDTO;
import com.ltc.telegrambotlinkedin.entity.Skill;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import com.ltc.telegrambotlinkedin.enums.UserStage;
import com.ltc.telegrambotlinkedin.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Slf4j
@Data
@Service
@RequiredArgsConstructor
public class TelegramBotService {
    private final TelegramBotClient bot;
    private final UserRepository userRepo;
    private final ModelMapper mpr;
    private int lastUpdateId;
    private final ArrayDeque<UserRequestDTO> queue = new ArrayDeque<>();

    /**
     * Gets raw updates starting from the last update id.
     * If any update presents maps them into UserRequestDTO and adds them into queue.
     * Starts the next stage of processing the requests.
     */
    public void getUpdates() {
        BotUpdatesDTO updates = bot.getUpdates(lastUpdateId);
        ArrayList<Result> results = updates.getResults();
        if (!results.isEmpty()) {
            this.lastUpdateId = results.getLast().getUpdate_id();
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
            UserOfBot user = userRepo.findUser(request.getChatId());
            String text = request.getText();
            switch (text) {
                case "/start" -> createUser(request);
                case "/new" -> user.setStage(UserStage.ENTERING_JOB);
                case "/edit" -> {
                    if (user != null) user.setStage(UserStage.ENTERING_JOB);
                }
                case "/delete" -> {
                    if (user != null) deleteUser(request);
                }
                default -> processRequests(request, user);
            }
        }
    }

    public UserResponseDTO createUser(UserRequestDTO request) {
        UserOfBot user = userRepo.findUser(request.getChatId());
        if (user == null) {
            user = mpr.map(request, UserOfBot.class);
            user.setStage(UserStage.CREATED);
            log.info(user.toString());
            user = userRepo.save(user);
            log.info(user.toString());
        }
        return mpr.map(user, UserResponseDTO.class);
    }

    public void processRequests(UserRequestDTO request, UserOfBot user) {
        UserStage stage = user.getStage();
        switch (stage) {
            case CREATED -> System.out.println();
            case ENTERING_JOB -> user.setJobTitle(request.getText());
            case ENTERING_SKILLS -> user.setSkillSet(findOrCreateSkills(request.getText()));
            case PROCESSED -> System.out.println();
            default -> System.out.println();
        }
    }

    List<Skill> findOrCreateSkills (String text) {
        Set<String> skills = Set.of(text.split("\\s*,\\s*"));
        return null;
    }

    public UserOfBot createJobSearch(Result result) {
        return null;
    }

    public UserOfBot updateUser(Result result) {
        return null;
    }

    public UserResponseDTO deleteUser(UserRequestDTO request) {
        UserOfBot user = userRepo.findUser(request.getChatId());
        userRepo.delete(user);
        return mpr.map(user, UserResponseDTO.class);
    }
}
