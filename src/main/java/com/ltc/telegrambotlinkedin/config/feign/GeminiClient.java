package com.ltc.telegrambotlinkedin.config.feign;

import com.ltc.telegrambotlinkedin.dto.geminiDTO.GeminiResponse;
import com.ltc.telegrambotlinkedin.dto.geminiDTO.GeminiRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "chatGPT", url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent")
public interface GeminiClient {

    @PostMapping
    GeminiResponse getMessageFeign(@RequestParam("key") String apiKey, @RequestBody GeminiRequest root);

}
