package com.ltc.telegrambotlinkedin.controller;

import com.ltc.telegrambotlinkedin.dto.jSearchDto.JSearchRoot;
import com.ltc.telegrambotlinkedin.service.JSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job")
public class JSearchController {
    private final JSearchService jsearchSvc;

    @GetMapping
    public JSearchRoot getJob (@RequestParam(name = "query") String query) {
        return jsearchSvc.getJobSearchResults(query);
    }
}
