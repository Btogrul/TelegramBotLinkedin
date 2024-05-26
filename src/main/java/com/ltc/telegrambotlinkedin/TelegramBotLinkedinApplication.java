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
    List<UserOfBot> mock () {
        return new ArrayList<>(List.of (
                new UserOfBot(1L, 100L, "userName1", "userSurname1",
                        "backend developer", UserStage.PROCESSED, "Baku", Locale.of("az-Latn-Az"),
                        true, new Date(), new Date(), new ArrayList<>(List.of (
                                new Skill(1L, "skill1"), new Skill(2L, "skill2"), new Skill(3L, "skill3")
                ))),

                new UserOfBot(2L, 200L, "userName2", "userSurname2",
                        "backend developer", UserStage.PROCESSED, "Baku", Locale.of("az-Latn-Az"),
                        true, new Date(), new Date(), new ArrayList<>(List.of (
                        new Skill(2L, "skill2"), new Skill(3L, "skill3"), new Skill(4L, "skill4")
                ))),

                new UserOfBot(3L, 300L, "userName3", "userSurname3",
                        "backend developer", UserStage.PROCESSED, "Baku", Locale.of("az-Latn-Az"),
                        true, new Date(), new Date(), new ArrayList<>(List.of (
                        new Skill(1L, "skill1"), new Skill(3L, "skill3"), new Skill(4L, "skill4")
                ))),

                new UserOfBot(3L, 400L, "userName4", "userSurname4",
                        "backend developer", UserStage.PROCESSED, "Baku", Locale.of("az-Latn-Az"),
                        true, new Date(), new Date(), new ArrayList<>(List.of (
                        new Skill(3L, "skill3"), new Skill(4L, "skill4")
                ))),

                new UserOfBot(4L, 500L, "userName5", "userSurname5",
                        "backend developer", UserStage.PROCESSED, "Baku", Locale.of("az-Latn-Az"),
                        false, new Date(), new Date(), new ArrayList<>(List.of (
                        new Skill(1L, "skill1"), new Skill(2L, "skill2"), new Skill(1L, "skill1")
                )))
        ));
    }
}
