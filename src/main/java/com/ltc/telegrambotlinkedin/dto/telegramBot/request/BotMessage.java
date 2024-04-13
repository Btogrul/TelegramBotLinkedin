package com.ltc.telegrambotlinkedin.dto.telegramBot.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.ArrayList;

@Data
public class BotMessage {
    @JsonProperty("message_id")
    private int message_id;
    @JsonProperty("from")
    private From from;
    @JsonProperty("chat")
    private Chat chat;
    @JsonProperty("date")
    private int date;
    @JsonProperty("text")
    private String text;
    @JsonProperty("entities")
    private ArrayList<Entity> entities;
}