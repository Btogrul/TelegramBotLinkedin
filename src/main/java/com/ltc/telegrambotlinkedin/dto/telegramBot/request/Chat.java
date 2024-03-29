package com.ltc.telegrambotlinkedin.dto.telegramBot.request;

import lombok.Data;

@Data
public class Chat {
    private int id;
    private String first_name;
    private String last_name;
    private String username;
    private String type;
}
