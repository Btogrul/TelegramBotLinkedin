package com.ltc.telegrambotlinkedin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableFeignClients
public class TelegramBotLinkedinApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotLinkedinApplication.class, args);
    }

}
