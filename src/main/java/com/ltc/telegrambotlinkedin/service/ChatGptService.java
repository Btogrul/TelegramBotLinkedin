package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.ChatGPTClient;
import com.ltc.telegrambotlinkedin.dto.gpt.GptRequestDto;
import com.ltc.telegrambotlinkedin.dto.others.Message;
import com.ltc.telegrambotlinkedin.dto.others.MessageResponseRoot;
import com.ltc.telegrambotlinkedin.dto.others.MessageRoot;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ChatGptService {
    private final ChatGPTClient chatGPTClient;
    @Value("${gptKey}")
    private String gptKey;
    @Value("${gptHost}")
    private String gptHost;


    public MessageResponseRoot getChat(GptRequestDto requestDto) {
        String finalMessage = requestDto.finalMessage();
        Message message = new Message();
        message.setContent(finalMessage);

        MessageRoot messageRoot = new MessageRoot();
        messageRoot.setMessages(new ArrayList<>());
        messageRoot.getMessages().add(message);

        return chatGPTClient.getMessageFeign(gptHost, gptKey, messageRoot);
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


    public List<String> analyzeJobDescription(String jobDescription) {
        List<String> requiredSkills = new ArrayList<>();

        if (jobDescription.contains("Java")) {
            requiredSkills.add("Java");
        }
        if (jobDescription.contains("Python")) {
            requiredSkills.add("Python");
        }

        return requiredSkills;
    }

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
