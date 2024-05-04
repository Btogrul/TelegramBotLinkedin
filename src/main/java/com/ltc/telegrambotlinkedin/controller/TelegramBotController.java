package com.ltc.telegrambotlinkedin.controller;

import com.ltc.telegrambotlinkedin.service.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("bot")
public class TelegramBotController {
    private final TelegramBotService ts;

    @Scheduled(fixedRate = 2000)
    @GetMapping("/updates")
    void getUpdates () {
        ts.getUpdates();
    }

}
