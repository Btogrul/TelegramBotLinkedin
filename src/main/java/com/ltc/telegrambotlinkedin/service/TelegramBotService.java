package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.TelegramBotClient;
import com.ltc.telegrambotlinkedin.dto.telegramBot.request.Result;
import com.ltc.telegrambotlinkedin.dto.telegramBot.request.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TelegramBotService {
    private final TelegramBotClient bot;

    public ArrayList<String> getMessages () {
        ArrayList<String> msgs = new ArrayList<>();
        ArrayList<Result> updates = bot.getUpdates().getResult();
        updates.forEach(u -> msgs.add(u.getMessage().getText()));
        return msgs;
    }

    public Root getUpdates() {
        return bot.getUpdates();
    }
}
