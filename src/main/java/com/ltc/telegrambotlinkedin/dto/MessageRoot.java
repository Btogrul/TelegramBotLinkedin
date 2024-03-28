package com.ltc.telegrambotlinkedin.dto;

import lombok.*;

import java.util.ArrayList;
@Data

public class MessageRoot {
    public ArrayList<Message> messages;
    public String system_prompt;
    public double temperature;
    public int top_k;
    public double top_p;
    public int max_tokens;
    public boolean web_access;
}
