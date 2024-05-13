package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.ChatGPTClient;
import com.ltc.telegrambotlinkedin.dto.gpt.GptRequestDto;
import com.ltc.telegrambotlinkedin.dto.jSearchDto.Job;
import com.ltc.telegrambotlinkedin.dto.others.Message;
import com.ltc.telegrambotlinkedin.dto.others.MessageResponseRoot;
import com.ltc.telegrambotlinkedin.dto.others.MessageRoot;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatGptService {
    @Value("${gptKey}")
    private String gptKey;
    @Value("${gptHost}")
    private String gptHost;

    private final ChatGPTClient chatGPTClient;

    public Map<UserOfBot, List<Job>> analyzeResults(Map<UserOfBot, List<Job>> foundJobs) {
        Map<UserOfBot, List<Job>> compatibleJobs = new HashMap<>();

        for (Map.Entry<UserOfBot, List<Job>> entry : foundJobs.entrySet()) {
            UserOfBot user = entry.getKey();
            List<Job> jobs = entry.getValue();

            List<Job> selectedJobs = new ArrayList<>();
            int limit = Math.min(jobs.size(), 5);
            for (int i = 0; i < limit; i++) {
                selectedJobs.add(jobs.get(i));
            }

            GptRequestDto gptRequestDto = new GptRequestDto();
            gptRequestDto.setMessage("Is this user at least 1 skill compatible with this vacancy? Please respond with 'yes' or 'no' : ");
            gptRequestDto.setUser(user);
            gptRequestDto.setJobs(selectedJobs);


            MessageRoot newMessage = new MessageRoot();

            ArrayList<Message> messages = new ArrayList<>();
            messages.add(Message.builder().content(gptRequestDto.toString()).build());

            newMessage.messages = messages;

            MessageResponseRoot response = chatGPTClient.getMessageFeign(gptHost, gptKey, newMessage);

            if (response.getResult().equalsIgnoreCase("yes")) {
                compatibleJobs.put(user, selectedJobs);
                log.info("Job added");
            } else {
                log.info("Nuh uh");
            }
        }

        return compatibleJobs;
    }








}
