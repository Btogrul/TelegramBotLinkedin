package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.JSearchClient;
import com.ltc.telegrambotlinkedin.dto.jSearchDto.Job;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import com.ltc.telegrambotlinkedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
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
        return jSearchClient.getSearch(jSearchHost, jSearchKey, query, 1, 10, "today", isRemote).getData();
    }

    public Map<UserOfBot, List<Job>> findJobsForUsers(List<UserOfBot> processedUsers) {
        HashMap<UserOfBot, List<Job>> result = new HashMap<>();
        for (UserOfBot user : processedUsers) {

            String query = "%s, %s".formatted(user.getJobTitle(), user.getUserLocation());
            boolean remote = user.isOnlyRemote();
            List<Job> jobSearchResults = new ArrayList<>();

            if (user.getUpdateDate() == null) {
                jobSearchResults = getAllJobs(query, remote);
                user.setUpdateDate(new Date());
            } else if (new Date().getTime() - user.getUpdateDate().getTime() > 86400){
                jobSearchResults = getTodaysJobs(query, remote);
                user.setUpdateDate(new Date());
            }

            user = userRepo.save(user);
            result.put(user, jobSearchResults);
        }
        return result;
    }
}
