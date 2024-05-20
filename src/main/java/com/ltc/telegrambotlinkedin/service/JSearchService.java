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

    public ArrayList<Job> getAllJobs(String query, Boolean isRemote) {
        return jSearchClient.getSearch(jSearchHost, jSearchKey, query, 1, 10, "all", isRemote).getData();
    }

    public ArrayList<Job> getTodaysJobs(String query, Boolean isRemote) {
        return jSearchClient.getSearch(jSearchHost, jSearchKey, query, 1, 10, "today" , isRemote).getData();
    }

    public Map<UserOfBot, List<Job>> findJobsForUsers(List<UserOfBot> processedUsers) {
        HashMap<UserOfBot, List<Job>> result = new HashMap<>();
        for (UserOfBot user : processedUsers) {

            String query = "%s, %s".formatted(user.getJobTitle(), user.getUserLocation());
            Boolean remote;
            if (user.getRemoteJob().equalsIgnoreCase("remote")) {
                remote = true;
            } else if (user.getRemoteJob().equalsIgnoreCase("office")) {
                remote = false;
            } else {
                remote = null;
            }
            List<Job> jobSearchResults;
            if (user.getUpdateDate() == null) {
                jobSearchResults = getAllJobs(query, remote);
            } else {
                jobSearchResults = getTodaysJobs(query, remote);
            }
            user.setUpdateDate(new Date());
            result.put(userRepo.save(user), jobSearchResults);

        }
        return result;

    }
}
