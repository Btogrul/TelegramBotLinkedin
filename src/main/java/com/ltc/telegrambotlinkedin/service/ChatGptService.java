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



//    public String getClass(MessageRoot messageRoot) {
//        MessageResponseRoot responseRoot = chatGPTClient.getMessageFeign(gptHost, gptKey, messageRoot);
//        return responseRoot.getResult();
//    }

//
//    public Map<UserOfBot, List<Job>> analyzeResults (Map<UserOfBot, List<Job>> foundJobs) {
//        Map<UserOfBot, List<Job>> suitableJobs = new HashMap<>();
//
//
//        for (Map.Entry<UserOfBot, List<Job>> entry : foundJobs.entrySet()) {
//            UserOfBot user = entry.getKey();
//            List<Job> jobs = entry.getValue();
//
//            for (Job job : jobs) {
//                GptRequestDto gptRequestDto = new GptRequestDto();
//                gptRequestDto.setMessage("Is this user compatible with this vacancy? Please respond with 'yes' or 'no' : ");
//                gptRequestDto.setUser(user);
//                gptRequestDto.setJobs(Collections.singletonList(job.getJobTitle()));
//
//
//                MessageRoot messageRoot = new MessageRoot();
//                messageRoot.setMessages(gptRequestDto.finalMessage());
//                messageRoot.setWeb_access(true);
//
//
//                try {
//                    MessageResponseRoot response = chatGPTClient.getMessageFeign(gptHost, gptKey, messageRoot);
//
//
//                    if ("yes".equalsIgnoreCase(response.getResult())) {
//                        suitableJobs.computeIfAbsent(user, k -> new ArrayList<>()).add(job);
//                    }
//                } catch (FeignException e) {
////                    System.out.println(e.getMessage());
//                    log.info(e.getMessage());
//                }
//            }
//        }
//
//        return suitableJobs;
//    }

//    public Map<UserOfBot, List<Job>> analyzeResults (Map<UserOfBot, List<Job>> foundJobs) {
//
//       return foundJobs;
//
//    }
//    public Map<UserOfBot, List<Job>> analyzeResults (Map<UserOfBot, List<Job>> foundJobs) {
//        for (Map.Entry<UserOfBot, List<Job>> entry : foundJobs.entrySet()) {
//            UserOfBot user = entry.getKey();
//            List<Job> jobs = entry.getValue();
//
//
//            ArrayList<Message> messages = new ArrayList<>();
//            messages.add(new Message(user.getJobTitle(), user.getSkillSet().toString(), jobs.toString()));
//
//
//            MessageRoot messageRoot = new MessageRoot();
//            messageRoot.setMessages(messages);
//            messageRoot.setWeb_access(true);
//
//
//            try {
//                MessageResponseRoot response = chatGPTClient.getMessageFeign(gptHost, gptKey, messageRoot);
//
//            } catch (FeignException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        return foundJobs;
//    }


//    public Map<UserOfBot, List<Job>> analyzeResults(Map<UserOfBot, List<Job>> foundJobs) {
//
//        String finalMessage = buildMessageContent(foundJobs);
//
//        Message message = new Message();
//        message.setContent(finalMessage);
//
//        MessageRoot messageRoot = new MessageRoot();
//        messageRoot.setMessages(new ArrayList<>());
//        messageRoot.getMessages().add(message);
//
//        try {
//            return (Map<UserOfBot, List<Job>>) chatGPTClient.getMessageFeign(gptHost, gptKey, messageRoot);
//        } catch (FeignException e) {
//            throw new RuntimeException("Error while calling ChatGPT API", e);
//        }
//    }
//
//    private String buildMessageContent(Map<UserOfBot, List<Job>> foundJobs) {
//        StringBuilder contentBuilder = new StringBuilder();
//
//        for (Map.Entry<UserOfBot, List<Job>> entry : foundJobs.entrySet()) {
//            UserOfBot user = entry.getKey();
//            List<Job> jobs = entry.getValue();
//
//            contentBuilder.append("User: ").append(user.getFirstName()).append("\n");
//
//            contentBuilder.append("Jobs:\n");
//            for (Job job : jobs) {
//                contentBuilder.append("- ").append(job.getJobTitle()).append(" at ").append(job.getEmployerName()).append("\n");
//            }
//            contentBuilder.append("\n");
//        }
//        return contentBuilder.toString();
//    }



//    public MessageResponseRoot getChat(GptRequestDto requestDto) {
//
//        String finalMessage = requestDto.finalMessage();
//        Message message = new Message();
//        message.setContent(finalMessage);
//
//        MessageRoot messageRoot = new MessageRoot();
//        messageRoot.setMessages(new ArrayList<>());
//        messageRoot.getMessages().add(message);
//        try {
//            return chatGPTClient.getMessageFeign(gptHost, gptKey, messageRoot);
//        } catch (FeignException e) {
//            throw new RuntimeException("Error ~~ while calling ChatGPT API", e);
//        }
//    }




}
