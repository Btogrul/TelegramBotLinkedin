package com.ltc.telegrambotlinkedin.config.feign;

import com.ltc.telegrambotlinkedin.dto.gpt.MessageResponseRoot;
import com.ltc.telegrambotlinkedin.dto.gpt.MessageRoot;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "chatGPT", url = "https://chatgpt-42.p.rapidapi.com/")
public interface ChatGPTClient {

    @PostMapping("gpt4")
    MessageResponseRoot getMessageFeign(@RequestHeader(name = "X-RapidAPI-Host") String host,
                                        @RequestHeader(name = "X-RapidAPI-Key") String key,
                                        @RequestBody MessageRoot root);

}
