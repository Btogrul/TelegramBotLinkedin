package com.ltc.telegrambotlinkedin.config.feign;

import com.ltc.telegrambotlinkedin.dto.telegramBot.request.BotRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "CareerCompanionBot", url = "https://api.telegram.org/bot7137674776:AAF1hFh06hxqM2IIhNduwfx5DHH20GGzFqw")
public interface TelegramBotClient {

    @GetMapping("/getUpdates")
    BotRequestDTO getUpdates ();

}
