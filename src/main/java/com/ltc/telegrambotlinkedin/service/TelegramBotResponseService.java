package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.TelegramBotClient;
import com.ltc.telegrambotlinkedin.dto.jSearchDto.Job;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import com.ltc.telegrambotlinkedin.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Data
@Service
@RequiredArgsConstructor
public class TelegramBotResponseService {

    private final UserRepository userRepo;
    private final JSearchService jSearchService;
    private final ChatGptService chatGptService;
    private final TelegramBotClient bot;

//    @Scheduled(cron = "0 55 23 * * *")
    @Scheduled(fixedRate = 3000)
    public void sendMessages() {
        List<UserOfBot> processed = userRepo.findAllUsers();
        Map<UserOfBot, List<Job>> results = jSearchService.findJobsForUsers(processed);
        results = chatGptService.analyzeResults(results);

        for (Map.Entry<UserOfBot, List<Job>> entry : results.entrySet()) {
            long chatId = entry.getKey().getChatId();
            List<Job> jobs = entry.getValue();
            for (Job job : jobs) {
                String jobApplyLink = job.getJobApplyLink();
                bot.sendMessage(chatId, "A new vacancy 🤩 %n%s".formatted(jobApplyLink));
            }
        }
    }
}
