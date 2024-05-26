package com.ltc.telegrambotlinkedin.dto.gpt.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MessageResponseRoot {
    private String result;
    private boolean status;
    private int server_code;
}
