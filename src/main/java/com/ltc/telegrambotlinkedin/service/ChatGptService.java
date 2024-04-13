package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.ChatGPTClient;
import com.ltc.telegrambotlinkedin.dto.others.MessageResponseRoot;
import com.ltc.telegrambotlinkedin.dto.others.MessageRoot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@RequiredArgsConstructor
@Slf4j
public class ChatGptService {
    private final ChatGPTClient chatGPTClient;
    @Value("${gptKey}")
    private static String gptKey;
    @Value("${gptHost}")
    private static String gptHost;

    public MessageResponseRoot getChat(@RequestBody MessageRoot messageRoot) {
        return chatGPTClient.getMessageFeign(gptHost, gptKey, messageRoot);
    }
//    MessageRoot root;
//
//    public MessageResponseRoot getChat(){
//      return  chatGPTClient.getMessageFeign(key, host, root);
//    };

}
