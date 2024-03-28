package com.ltc.telegrambotlinkedin.controller;

import com.ltc.telegrambotlinkedin.config.feign.ChatGPTClient;
import com.ltc.telegrambotlinkedin.dto.MessageResponseRoot;
import com.ltc.telegrambotlinkedin.dto.MessageRoot;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gpt")
public class BotController {

    private final ChatGPTClient chatGPTClient;

    private final String gptKey = "a005368adfmsh9475ffec9ce47d5p19cc0cjsn3af963af6e73";
    private final String gptHost = "chatgpt-42.p.rapidapi.com";

    @PostMapping
    public MessageResponseRoot getChat(@RequestBody MessageRoot messageRoot) {
        return chatGPTClient.getMessageFeign(gptKey, gptHost, messageRoot);
    }
}
