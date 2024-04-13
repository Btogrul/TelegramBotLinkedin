package com.ltc.telegrambotlinkedin.dto.telegramBot.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class From {
    @JsonProperty("id")
    private long id;
    @JsonProperty("is_bot")
    private boolean is_bot;
    @JsonProperty("first_name")
    private String first_name;
    @JsonProperty("last_name")
    private String last_name;
    @JsonProperty("username")
    private String username;
    @JsonProperty("language_code")
    private String language_code;
}
