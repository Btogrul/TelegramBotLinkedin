package com.ltc.telegrambotlinkedin.config.feign;

import com.ltc.telegrambotlinkedin.dto.others.MessageResponseRoot;
import com.ltc.telegrambotlinkedin.dto.others.MessageRoot;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "chatGPT", url = "https://chatgpt-42.p.rapidapi.com/")
public interface ChatGPTClient {

    @PostMapping("conversationgpt4")
    MessageResponseRoot getMessageFeign(@RequestHeader("X-RapidAPI-Key") String key, @RequestHeader("X-RapidAPI-Host") String host, @RequestBody MessageRoot root);

}
