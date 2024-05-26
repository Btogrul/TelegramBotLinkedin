package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.ChatGPTClient;
import com.ltc.telegrambotlinkedin.dto.jSearchDto.Job;
import com.ltc.telegrambotlinkedin.dto.gpt.response.MessageResponseRoot;
import com.ltc.telegrambotlinkedin.dto.gpt.request.MessageRoot;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatGptService {
    @Value("${gptKey}")
    public String gptKey;
    @Value("${gptHost}")
    public String gptHost;

    private final ModelMapper mpr;
    private final ChatGPTClient chatGPTClient;

    public Map<UserOfBot, List<Job>> analyzeResults(Map<UserOfBot, List<Job>> foundJobs) {
        log.info("Analyze results method:");
        for (Map.Entry<UserOfBot, List<Job>> entry : foundJobs.entrySet()) {
            UserOfBot user = entry.getKey();
            log.info("The user skill set: {}", user.getSkillSet());
            List<Job> jobs = entry.getValue();
            log.info("Jobs: {}", jobs.size());
            String content = buildContent(user, jobs);
            log.info("Content: {}", content);
            MessageRoot messageRoot = mpr.map(content, MessageRoot.class);
            MessageResponseRoot gptResponse = chatGPTClient.getMessageFeign(gptHost, gptKey, messageRoot);

            String result = gptResponse.getResult();
            log.info("Result: {}", result);
            String[] results = result.split("\\s*,\\s*");
            ArrayList<Job> suitableJobs = new ArrayList<>(jobs);
            if (results.length == jobs.size()) {
                for (int i = 0; i < jobs.size(); i++) {
                    if (results[i].equalsIgnoreCase("yes")) {
                        suitableJobs.add(jobs.get(i));
                    }
                }
            } else {
                log.info("Gpt response error: %d != %d".formatted(results.length, jobs.size()));
            }
            foundJobs.put(user, suitableJobs);
        }
        return foundJobs;
    }

    public String buildContent(UserOfBot user, List<Job> jobs) {
        StringBuilder jobDescList = new StringBuilder();
        for (int i = 0; i < jobs.size(); i++) {
            jobDescList.append("job #%d:\n%s\n".formatted(i, jobs.get(i).getJobDescription()));
        }
        return """
                Following is the skill set of the applicant:
                %s
                Analyze the suitability of the following jobs:
                %s
                Answer only a comma delimited list of "yes" or "no" for each job
                Example: yes, yes, no, yes, no""".formatted(jobDescList, user.getSkillSet().toString());
    }
}
