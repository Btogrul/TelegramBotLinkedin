package com.ltc.telegrambotlinkedin;

import com.ltc.telegrambotlinkedin.entity.Skill;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import com.ltc.telegrambotlinkedin.enums.UserStage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@EnableScheduling
@SpringBootApplication
@EnableFeignClients
public class TelegramBotLinkedinApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotLinkedinApplication.class, args);
    }

}
