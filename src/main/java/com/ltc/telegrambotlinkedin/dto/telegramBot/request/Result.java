package com.ltc.telegrambotlinkedin.dto.telegramBot.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Result {
    @JsonProperty("update_id")
    private long update_id;
    @JsonProperty("message")
    private BotMessage message;
}
