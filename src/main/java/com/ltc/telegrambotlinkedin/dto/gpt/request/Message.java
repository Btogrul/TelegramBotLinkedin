package com.ltc.telegrambotlinkedin.dto.gpt.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Message {
    private final String role = "user";
    private String content;
}
