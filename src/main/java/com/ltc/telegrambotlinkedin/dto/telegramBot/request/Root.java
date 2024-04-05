package com.ltc.telegrambotlinkedin.dto.telegramBot.request;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Root {
    private boolean ok;
    private ArrayList<Result> result;
}