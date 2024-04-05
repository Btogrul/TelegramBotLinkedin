package com.ltc.telegrambotlinkedin.dto.telegramBot.request;

import lombok.Data;

@Data
public class Entity {
    private int offset;
    private int length;
    private String type;
}
