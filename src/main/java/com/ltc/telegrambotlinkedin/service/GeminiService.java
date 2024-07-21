package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.GeminiClient;
import com.ltc.telegrambotlinkedin.dto.jSearchDTO.Job;
import com.ltc.telegrambotlinkedin.dto.geminiDTO.GeminiRequest;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiService {
    @Value("${GOOGLE_API_KEY}")
    public String apiKey;

    private final GeminiClient geminiClient;

    public Map<UserOfBot, List<Job>> analyzeResults(Map<UserOfBot, List<Job>> foundJobs) {
        log.info("Analyzing results. User count: {}", foundJobs.size());
        for (Map.Entry<UserOfBot, List<Job>> entry : foundJobs.entrySet()) {

            List<Job> jobs = entry.getValue();
            if (!jobs.isEmpty()) {

                UserOfBot user = entry.getKey();
                var geminiRequest = buildGeminiRequest(user, jobs);
                var gptResponse = geminiClient.getMessageFeign(apiKey, geminiRequest);

                String[] results = gptResponse.getResponse().split("\\b(yes|no|YES|NO)\\b");
                log.info("The results: {}", Arrays.toString(results));

                ArrayList<Job> suitableJobs = new ArrayList<>();
                if (results.length == jobs.size()) {
                    for (int i = 0; i < jobs.size(); i++) {
                        if (results[i].equalsIgnoreCase("yes")) {
                            suitableJobs.add(jobs.get(i));
                        }
                    }
                } else {
                    log.error("Gemini response error: {} != {}", results.length, jobs.size());
                }
                log.info("Suitable Jobs: {}", suitableJobs.size());
                foundJobs.replace(user, suitableJobs);
            }
        }
        return foundJobs;
    }

    private GeminiRequest buildGeminiRequest(UserOfBot user, List<Job> jobs) {
        log.info("The user {} and skill set: {}", user.getFirstName(), user.getSkillSet());
        String content = buildContent(user, jobs);
        log.info("The Gemini query:\n {}", content.lines());
        return new GeminiRequest(content);
    }

    private String buildContent(UserOfBot user, List<Job> jobs) {
        StringBuilder jobDescList = new StringBuilder();
        for (int i = 0; i < jobs.size(); i++) {
            jobDescList.append("job #%d:\n%s\n".formatted(i+1, jobs.get(i).getJobDescription()));
        }
        return """
                Following is the skill set of the applicant:
                %s
                Analyze the suitability of the following jobs:
                %s
                Answer only a comma delimited list of "yes" or "no" for each job. Yes means user's skill set is suitable for the job.
                Example: yes, yes, no, yes, no""".formatted(user.getSkillSet().toString(), jobDescList);
    }
}