package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.JSearchClient;
import com.ltc.telegrambotlinkedin.dto.jSearchDTO.Job;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import com.ltc.telegrambotlinkedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    public ArrayList<Job> getJobs(String query,String datePosted, Boolean isRemote) {
        return jSearchClient.getSearch(jSearchHost, jSearchKey, query, 1, 10, datePosted, isRemote).getData();
    }

    public Map<UserOfBot, List<Job>> findJobsForUsers(List<UserOfBot> processedUsers) {
        HashMap<UserOfBot, List<Job>> result = new HashMap<>();
        for (UserOfBot user : processedUsers) {

            String query = "%s, %s".formatted(user.getJobTitle(), user.getUserLocation());
            boolean remote = user.isOnlyRemote();
            List<Job> jobSearchResults = new ArrayList<>();

            if (user.getUpdateDate() == null) {
                jobSearchResults = getJobs(query, "all", remote);
                user.setUpdateDate(new Date());
                log.info("Fetched all posts for {}. Number of jobs: {}", user.getFirstName(), jobSearchResults.size());

            } else if ((Instant.now().toEpochMilli() - user.getUpdateDate().getTime()) >= TimeUnit.DAYS.toMillis(1)){
                jobSearchResults = getJobs(query,"today", remote);
                user.setUpdateDate(new Date());
                log.info("Fetched today's posts for {}. Number of jobs: {}", user.getFirstName(), jobSearchResults.size());
            }
            user = userRepo.save(user);
            if (!jobSearchResults.isEmpty()) {
                result.put(user, jobSearchResults);
            }
        }
        return result;
    }
}