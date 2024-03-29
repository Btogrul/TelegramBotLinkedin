package com.ltc.telegrambotlinkedin.controller;

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
    ArrayList<String> getUpdates () {
        return ts.getMessages();
    }
}
