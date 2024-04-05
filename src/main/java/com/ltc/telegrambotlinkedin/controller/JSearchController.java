package com.ltc.telegrambotlinkedin.controller;

import com.ltc.telegrambotlinkedin.config.feign.JSearchClient;
import com.ltc.telegrambotlinkedin.dto.MessageRoot;
import com.ltc.telegrambotlinkedin.dto.jSearchDto.JSearchRoot;
import com.ltc.telegrambotlinkedin.dto.jSearchDto.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job")
public class JSearchController {
private final JSearchClient jSearchClient;
    private final String jSearchKey = "a005368adfmsh9475ffec9ce47d5p19cc0cjsn3af963af6e73";
    private final String jSearchHost = "jsearch.p.rapidapi.com";



    @GetMapping
    public JSearchRoot getJobSearchResults(@RequestParam String query, @RequestParam Integer page, @RequestParam Integer numPages) {


        return jSearchClient.getSearch(jSearchKey, jSearchHost, query, page, numPages);
    }

}