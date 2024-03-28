package com.ltc.telegrambotlinkedin.dto.telegramBot.request;

import lombok.Data;

@Data
public class From {
    private int id;
    private boolean is_bot;
    private String first_name;
    private String last_name;
    private String username;
    private String language_code;
}
