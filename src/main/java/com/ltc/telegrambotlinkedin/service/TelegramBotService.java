package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.TelegramBotClient;
import com.ltc.telegrambotlinkedin.dto.telegramBot.request.BotRequestDTO;
import com.ltc.telegrambotlinkedin.dto.telegramBot.request.Result;
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

    public BotRequestDTO getUpdates() {
        return bot.getUpdates();
    }
}
