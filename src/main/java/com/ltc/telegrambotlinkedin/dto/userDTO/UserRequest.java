package com.ltc.telegrambotlinkedin.dto.userDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRequest {
    @JsonProperty("date")
    private int date;
    @JsonProperty("text")
    private String text;
    @JsonProperty("chatId")
    private long chatId;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("language_code")
    private String languageCode;

    @Override
    public String toString() {
        return "date= " + date + " || chatId= " + chatId +
                " || " + firstName + " " + lastName + " :[" + languageCode + "] " + text;
    }
}