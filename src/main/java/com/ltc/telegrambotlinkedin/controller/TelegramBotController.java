package com.ltc.telegrambotlinkedin.controller;

import com.ltc.telegrambotlinkedin.dto.telegramBot.request.BotRequestDTO;
import com.ltc.telegrambotlinkedin.service.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("bot")
public class TelegramBotController {
    private final TelegramBotService ts;

    @GetMapping("/updates")
    BotRequestDTO getUpdates () {
        return ts.getUpdates();
    }

}
