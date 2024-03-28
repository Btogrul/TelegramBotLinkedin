package com.ltc.telegrambotlinkedin.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MessageResponseRoot {
    public String result;
    public boolean status;
    public int server_code;
}
