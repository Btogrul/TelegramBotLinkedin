package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.TelegramBotClient;
import com.ltc.telegrambotlinkedin.dto.jSearchDto.Job;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import com.ltc.telegrambotlinkedin.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Data
@Service
@RequiredArgsConstructor
public class TelegramBotResponseService {

    private final UserRepository userRepo;
    private final JSearchService jSearchService;
    private final ChatGptService chatGptService;
    private final TelegramBotClient bot;

    public void sendMessages() {
        List<UserOfBot> processed = userRepo.findAllUsers();
        Map<UserOfBot, List<Job>> results = jSearchService.findJobsForUsers(processed);
        results = chatGptService.analyzeResults(results);

        for (Map.Entry<UserOfBot, List<Job>> entry : results.entrySet()) {
            long chatId = entry.getKey().getChatId();
            List<Job> jobs = entry.getValue();
            for (Job job : jobs) {
                String jobApplyLink = job.getJobApplyLink();
                String jobJobTitle = job.getJob_job_title();
                bot.sendMessage(chatId, "%s%n%s".formatted(jobJobTitle, jobApplyLink));
            }
        }
    }
}
