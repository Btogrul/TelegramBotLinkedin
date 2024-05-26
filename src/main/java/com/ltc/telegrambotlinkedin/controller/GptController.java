package com.ltc.telegrambotlinkedin.controller;

import com.ltc.telegrambotlinkedin.config.feign.ChatGPTClient;
import com.ltc.telegrambotlinkedin.dto.gpt.response.MessageResponseRoot;
import com.ltc.telegrambotlinkedin.dto.gpt.request.MessageRoot;
import com.ltc.telegrambotlinkedin.dto.jSearchDto.Job;
import com.ltc.telegrambotlinkedin.entity.Skill;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import com.ltc.telegrambotlinkedin.enums.UserStage;
import com.ltc.telegrambotlinkedin.service.ChatGptService;
import com.ltc.telegrambotlinkedin.service.JSearchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/gpt")
public class GptController {

    private final ChatGPTClient chatGPTClient;
    private final ChatGptService chatGptService;
    private final JSearchService jSearchService;

//    @GetMapping("/ans")
//    public Result getAnswers () {
//        var user = new UserOfBot(1L, 100L, "userName1", "userSurname1",
//                "backend developer", UserStage.PROCESSED, "Berlin", Locale.of("az-Latn-Az"),
//                false, new Date(), new Date(), new ArrayList<>(List.of (
//                new Skill(1L, "Java"), new Skill(2L, "Spring"),
//                new Skill(3L, "SQL"), new Skill (4L, "Docker")
//        )));
//
//        ArrayList<Job> allJobs = jSearchService.getAllJobs("backend developer, Berlin", false);
//        MessageRoot message = chatGptService.getMessageRoot(user, allJobs);
//        MessageResponseRoot messageFeign = chatGPTClient.getMessageFeign(chatGptService.gptHost, chatGptService.gptKey, message);
//        return new Result(message, messageFeign);
//    }
//
//    static class Result {
//        MessageRoot messageRoot;
//        MessageResponseRoot messageResponseRoot;
//
//        public Result(MessageRoot messageRoot, MessageResponseRoot messageResponseRoot) {
//            this.messageRoot = messageRoot;
//            this.messageResponseRoot = messageResponseRoot;
//        }
//    }
}
