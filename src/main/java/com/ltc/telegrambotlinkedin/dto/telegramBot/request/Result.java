package com.ltc.telegrambotlinkedin.dto.telegramBot.request;

import lombok.Data;

@Data
public class Result {
    private int update_id;
    private Message message;
}
