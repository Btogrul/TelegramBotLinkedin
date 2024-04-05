package com.ltc.telegrambotlinkedin.controller;

import com.ltc.telegrambotlinkedin.config.feign.ChatGPTClient;
import com.ltc.telegrambotlinkedin.dto.others.MessageResponseRoot;
import com.ltc.telegrambotlinkedin.dto.others.MessageRoot;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/gpt")
public class GptController {
    private final ChatGPTClient chatGPTClient;

    private final String gptKey = "a005368adfmsh9475ffec9ce47d5p19cc0cjsn3af963af6e73";
    private final String gptHost = "chatgpt-42.p.rapidapi.com";

    @PostMapping
    public MessageResponseRoot getChat(@RequestBody MessageRoot messageRoot) {
        return chatGPTClient.getMessageFeign(gptKey, gptHost, messageRoot);
    }
}
