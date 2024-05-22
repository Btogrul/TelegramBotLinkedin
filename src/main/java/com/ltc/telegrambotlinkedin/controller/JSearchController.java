package com.ltc.telegrambotlinkedin.controller;

import com.ltc.telegrambotlinkedin.dto.jSearchDto.Job;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import com.ltc.telegrambotlinkedin.enums.UserStage;
import com.ltc.telegrambotlinkedin.service.JSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job")
public class JSearchController {
    private final JSearchService jSearchService;

    @GetMapping("/getJob")
    public ArrayList<Job> getJob (@RequestParam(name = "query") String query, @RequestParam (name= "remote_jobs_only") Boolean isRemote) {
        return jSearchService.getAllJobs(query, isRemote);
    }

    @GetMapping("/getUsers")
    public Map<UserOfBot, List<Job>> getProcessed () {
        return jSearchService.findJobsForUsers(mock());
    }

    List<UserOfBot> mock () {
        List<UserOfBot> users = new ArrayList<>();
        // Creating UserOfBot instances
        UserOfBot user1 = new UserOfBot();
        user1.setChatId(123456789); // Example chat id
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setJobTitle("Software Engineer");
        user1.setStage(UserStage.PROCESSED); // Example stage
        user1.setUserLocation("New York");
        user1.setCreationDate(new Date());
        user1.setUpdateDate(new Date());

        UserOfBot user2 = new UserOfBot();
        user2.setChatId(987654321); // Example chat id
        user2.setFirstName("Alice");
        user2.setLastName("Smith");
        user2.setJobTitle("Data Scientist");
        user2.setStage(UserStage.PROCESSED); // Example stage
        user2.setUserLocation("San Francisco");
        user2.setCreationDate(new Date());
        user2.setUpdateDate(new Date());

        UserOfBot user3 = new UserOfBot();
        user3.setChatId(555555555); // Example chat id
        user3.setFirstName("Bob");
        user3.setLastName("Johnson");
        user3.setJobTitle("Product Manager");
        user3.setStage(UserStage.PROCESSED); // Example stage
        user3.setUserLocation("London");
        user3.setCreationDate(new Date());
        user3.setUpdateDate(new Date());

        // Adding users to the list
        users.add(user1);
        users.add(user2);
        users.add(user3);
        return users;
    }
}
