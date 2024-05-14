package com.ltc.telegrambotlinkedin.dto.others;

import lombok.*;

import java.util.ArrayList;

@Data

public class MessageRoot {
    public ArrayList<Message> messages;
    public boolean web_access;
}
