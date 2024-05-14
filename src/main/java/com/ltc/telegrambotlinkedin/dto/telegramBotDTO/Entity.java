package com.ltc.telegrambotlinkedin.dto.telegramBotDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Entity {
    @JsonProperty("offset")
    private int offset;
    @JsonProperty("length")
    private int length;
    @JsonProperty("type")
    private String type;
}
