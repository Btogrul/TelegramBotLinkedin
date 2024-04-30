package com.ltc.telegrambotlinkedin.controller;

import com.ltc.telegrambotlinkedin.dto.gpt.GptRequestDto;
import com.ltc.telegrambotlinkedin.dto.others.MessageResponseRoot;
import com.ltc.telegrambotlinkedin.service.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gpt")
public class GptController {
    @Autowired
    private ChatGptService chatGptService;

    //    @PostMapping
//    public boolean gpt(@RequestBody GptRequestDto requestDto) {
//        MessageResponseRoot responseRoot = chatGptService.getChat(requestDto);
//        String response = responseRoot.getResult();
//
//        if (response.contains("yes")) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    @PostMapping
    public boolean gpt(@RequestBody GptRequestDto requestDto) {
        MessageResponseRoot responseRoot = chatGptService.getChat(requestDto);
        String response = responseRoot.getResult();

        return response.contains("yes");
    }
}