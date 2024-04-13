package com.ltc.telegrambotlinkedin.dto.userDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRequestDTO {
    @JsonProperty("date")
    private int date;
    @JsonProperty("text")
    private String text;
    @JsonProperty("chatId")
    private int chatId;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;

    @Override
    public String toString() {
        return "date= " + date + " || chatId= " + chatId +
                " || " + firstName + " " + lastName + " : " + text;
    }
}
