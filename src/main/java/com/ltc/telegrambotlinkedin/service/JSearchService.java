package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.JSearchClient;
import com.ltc.telegrambotlinkedin.dto.jSearchDto.Job;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import com.ltc.telegrambotlinkedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class JSearchService {
    @Value("${jSearchKey}")
    private String jSearchKey;
    @Value("${jSearchHost}")
    private String jSearchHost;

    private final JSearchClient jSearchClient;
    private final UserRepository userRepo;

    public ArrayList<Job> getAllJobs(String query) {
        return jSearchClient.getSearch(jSearchHost, jSearchKey, query, 1, 10, "all").getData();
    }

    public ArrayList<Job> getTodaysJobs(String query) {
        return jSearchClient.getSearch(jSearchHost, jSearchKey, query, 1, 10, "today").getData();
    }

    public Map<UserOfBot, List<Job>> findJobsForUsers(List<UserOfBot> processedUsers) {
        HashMap<UserOfBot, List<Job>> result = new HashMap<>();
        for (UserOfBot user : processedUsers) {

            String query = "%s, %s".formatted(user.getJobTitle(), user.getUserLocation());
            List<Job> jobSearchResults;
            if (user.getUpdateDate() == null) {
                jobSearchResults = getAllJobs(query);
            } else {
                jobSearchResults = getTodaysJobs(query);
            }
            user.setUpdateDate(new Date());
            result.put(userRepo.save(user), jobSearchResults);

        }
        return result;

    }
}
