package com.ltc.telegrambotlinkedin.config.feign;

import com.ltc.telegrambotlinkedin.dto.jSearchDto.JSearchRoot;
import com.ltc.telegrambotlinkedin.dto.jSearchDto.Parameters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "jSearch", url = "https://jsearch.p.rapidapi.com/")
public interface JSearchClient {

    @GetMapping("/search")
    JSearchRoot getSearch(@RequestHeader(name = "X-RapidAPI-Key") String key,
                          @RequestHeader(name = "X-RapidAPI-Host") String host,
                          @RequestParam("query") String query,
                          @RequestParam("page") Integer page,
                          @RequestParam("numPages") Integer numPages);


}
