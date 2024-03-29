package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.ChatGPTClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ChatGptService {
    private final ChatGPTClient chatGPTClient;
//    MessageRoot root;
//
//    public MessageResponseRoot getChat(){
//      return  chatGPTClient.getMessageFeign(key, host, root);
//    };

}
