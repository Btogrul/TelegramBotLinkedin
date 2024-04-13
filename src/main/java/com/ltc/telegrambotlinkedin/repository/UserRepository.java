package com.ltc.telegrambotlinkedin.repository;

import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserOfBot, Long> {
    @Query("SELECT u FROM UserOfBot u WHERE u.chatId = :chatId")
    UserOfBot findUser(@Param(value = "chatId") long chatId);
}
