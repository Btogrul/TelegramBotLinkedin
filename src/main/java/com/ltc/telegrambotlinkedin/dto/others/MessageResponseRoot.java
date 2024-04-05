package com.ltc.telegrambotlinkedin.dto.others;

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
