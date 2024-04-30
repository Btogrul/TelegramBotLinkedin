package com.ltc.telegrambotlinkedin.controller;

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
    public void getJob (@RequestParam(name = "jobTitle") String jobTitle,
                        @RequestParam(name = "location") String location) {
        jsearchSvc.getJobSearchResults(jobTitle, location);
    }
}
