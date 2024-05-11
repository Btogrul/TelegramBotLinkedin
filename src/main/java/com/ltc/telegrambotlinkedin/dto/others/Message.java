package com.ltc.telegrambotlinkedin.dto.others;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Message {

    public final String role = "user";
    public String content;

}
