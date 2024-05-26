package com.ltc.telegrambotlinkedin.repository;

import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserOfBot, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM user_of_bot u WHERE u.chat_id = :chat_id")
    UserOfBot findUser(@Param(value = "chat_id") long chat_id);

    @Query("SELECT u FROM UserOfBot u WHERE u.stage = 6")
    List<UserOfBot> findAllUsers();
}
