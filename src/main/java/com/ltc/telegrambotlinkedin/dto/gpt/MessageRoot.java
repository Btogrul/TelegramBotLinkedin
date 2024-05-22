package com.ltc.telegrambotlinkedin.dto.gpt;

import lombok.*;

import java.util.ArrayList;

@Data

public class MessageRoot {
    public ArrayList<Message> messages;
    public boolean web_access;
}
