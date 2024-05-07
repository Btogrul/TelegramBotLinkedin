package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.ChatGPTClient;
import com.ltc.telegrambotlinkedin.dto.gpt.GptRequestDto;
import com.ltc.telegrambotlinkedin.dto.jSearchDto.Job;
import com.ltc.telegrambotlinkedin.dto.others.Message;
import com.ltc.telegrambotlinkedin.dto.others.MessageResponseRoot;
import com.ltc.telegrambotlinkedin.dto.others.MessageRoot;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatGptService {
    @Value("${gptKey}")
    private String gptKey;
    @Value("${gptHost}")
    private String gptHost;

    private final ChatGPTClient chatGPTClient;

    public Map<UserOfBot, List<Job>> analyzeResults (Map<UserOfBot, List<Job>> foundJobs) {
        return foundJobs;
    }


//    public MessageResponseRoot getChat(GptRequestDto requestDto) {
//        String finalMessage = requestDto.finalMessage();
//        Message message = new Message();
//        message.setContent(finalMessage);
//
//
//        MessageRoot messageRoot = new MessageRoot();
//        messageRoot.setMessages(new ArrayList<>());
//        messageRoot.getMessages().add(message);
//
//
//        return chatGPTClient.getMessageFeign(gptHost, gptKey, messageRoot);
//    }


    public MessageResponseRoot getChat(GptRequestDto requestDto) {

        String finalMessage = requestDto.finalMessage();
        Message message = new Message();
        message.setContent(finalMessage);

        MessageRoot messageRoot = new MessageRoot();
        messageRoot.setMessages(new ArrayList<>());
        messageRoot.getMessages().add(message);
        try {
            return chatGPTClient.getMessageFeign(gptHost, gptKey, messageRoot);
        } catch (FeignException e) {
            throw new RuntimeException("Error ~~ while calling ChatGPT API", e);
        }
    }


//    public MessageResponseRoot getChat(@RequestBody List<MessageRoot> messageRoot) {
//        return chatGPTClient.getMessageFeign(gptHost, gptKey, messageRoot);
//    }
//
//
//    public MessageResponseRoot getChat(GptRequestDto requestDto) {
//        MessageRoot messageRoot = new MessageRoot();
//        messageRoot.setMessage(requestDto.finalMessage());
//        return chatGPTClient.getMessageFeign(gptHost, gptKey, Arrays.asList(messageRoot));
//    }


//    public boolean analyzeJobAndUser(String jobDescription, List<String> userSkills) {

//        Gpt4Request request = new Gpt4Request(jobDescription, userSkills);
//
//
//        Gpt4Response response = gpt4Service.analyze(request);
//
//
//        boolean isJobSuitable = response.isJobSuitable();
//
//        return isJobSuitable;
//    }
//


//    public String getChatResponse(String message) {
//        MessageRoot messageRoot = new MessageRoot();
//        messageRoot.setMessages(Arrays.asList(
//                new Message(message)
//        ));
//
//        MessageGPT responseRoot = chatGPTClient.getMessageFeign(gptKey, gptHost, messageRoot);
//        String response = responseRoot.getChoices().get(0).getClass().get().getText();
//        return response;
//    }

//    MessageRoot root;
//
//    public MessageResponseRoot getChat(){
//      return  chatGPTClient.getMessageFeign(key, host, root);
//    };

}
