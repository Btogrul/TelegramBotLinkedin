package com.ltc.telegrambotlinkedin.dto.telegramBotDTOs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Chat{
    @JsonProperty("id")
    private long chatId;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("username")
    private String username;
    @JsonProperty("type")
    private String type;
}
