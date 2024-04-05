package com.ltc.telegrambotlinkedin.dto.telegramBot.request;

import lombok.Data;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
@Data
public class Chat{
    private int id;
    private String first_name;
    private String last_name;
    private String username;
    private String type;
}

