package com.ltc.telegrambotlinkedin.controller;

import com.ltc.telegrambotlinkedin.dto.gpt.GptRequestDto;
import com.ltc.telegrambotlinkedin.dto.others.MessageResponseRoot;
import com.ltc.telegrambotlinkedin.dto.others.MessageRoot;
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
    public GptController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @Autowired
    private ChatGptService chatGptService;

}
