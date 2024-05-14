package com.ltc.telegrambotlinkedin.dto.telegramBotDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class BotUpdatesDTO {
    @JsonProperty("ok")
    private boolean ok;
    @JsonProperty("result")
    private ArrayList<Result> results;
}