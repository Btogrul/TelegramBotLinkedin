package com.ltc.telegrambotlinkedin.dto.telegramBot.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Chat{
    @JsonProperty("id")
    private int chatId;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("username")
    private String username;
    @JsonProperty("type")
    private String type;
}

