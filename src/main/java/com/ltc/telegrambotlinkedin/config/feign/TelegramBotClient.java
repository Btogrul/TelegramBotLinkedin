package com.ltc.telegrambotlinkedin.config.feign;

import com.ltc.telegrambotlinkedin.dto.telegramBotDTO.BotUpdatesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "CareerCompanionBot", url = "https://api.telegram.org/bot${botToken}")
public interface TelegramBotClient {
    @GetMapping("/getUpdates")
    BotUpdatesDTO getUpdates (@RequestParam(name = "offset") long offset);

    @PostMapping("/sendMessage")
    void sendMessage(
            @RequestParam(name = "chat_id") long chatId,
            @RequestParam(name = "text") String text
    );
}
