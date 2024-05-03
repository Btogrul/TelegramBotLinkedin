package com.ltc.telegrambotlinkedin.dto.gpt;

import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import lombok.Data;

import java.util.List;

@Data
public class GptRequestDto {
    private String message = ("is this user compatible with this vacancy, say yes or no : ");
    private UserOfBot user;
    private List<String> jobs;

    public String finalMessage() {
        return message +  " user skills :  " + user.getSkillSet() +
                ". user job title " +  user.getJobTitle() + ". vacancy : " +  jobs;
    }
}