package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.TelegramBotClient;
import com.ltc.telegrambotlinkedin.dto.jSearchDTO.Job;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import com.ltc.telegrambotlinkedin.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Data
@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramBotResponseService {

    private final UserRepository userRepo;
    private final JSearchService jSearchService;
    private final GeminiService geminiService;
    private final TelegramBotClient bot;

    @Scheduled(fixedDelay = 3000, initialDelay = 3000)
    public void sendMessages() {
        List<UserOfBot> processed = userRepo.findAllUsers();

        if (!processed.isEmpty()) {
            log.info("Processed users: {}", processed.size());
            Map<UserOfBot, List<Job>> results = jSearchService.findJobsForUsers(processed);

            if (!results.isEmpty()) {
                log.info("Users processed by jSearch: {}", processed.size());
                results = geminiService.analyzeResults(results);

                for (Map.Entry<UserOfBot, List<Job>> entry : results.entrySet()) {
                    long chatId = entry.getKey().getChatId();
                    List<Job> jobs = entry.getValue();
                    log.info("Matching jobs for {}: {}", entry.getKey().getFirstName(), jobs.size());

                    for (Job job : jobs) {
                        String jobApplyLink = job.getJobApplyLink();
                        bot.sendMessage(chatId, "A new vacancy 🤩 %n%s".formatted(jobApplyLink));
                    }
                }
            }
        }
    }
}