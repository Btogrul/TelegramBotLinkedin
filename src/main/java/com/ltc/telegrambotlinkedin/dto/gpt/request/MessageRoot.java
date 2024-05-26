package com.ltc.telegrambotlinkedin.dto.gpt.request;

import lombok.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class MessageRoot {
    private ArrayList<Message> messages;
    private boolean web_access;
}
