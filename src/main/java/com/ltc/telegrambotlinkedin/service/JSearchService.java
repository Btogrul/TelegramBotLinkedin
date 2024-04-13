package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.JSearchClient;
import com.ltc.telegrambotlinkedin.dto.jSearchDto.JSearchRoot;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Service
public class JSearchService {
    private final JSearchClient jSearchClient;

    @Value("${jSearchKey}")
    private static String jSearchKey;
    @Value("${jSearchHost}")
    private static String jSearchHost;

    @GetMapping
    public JSearchRoot getJobSearchResults(@RequestParam String query, @RequestParam Integer page, @RequestParam Integer numPages) {


        return jSearchClient.getSearch(jSearchKey, jSearchHost, query, page, numPages);
    }
}
