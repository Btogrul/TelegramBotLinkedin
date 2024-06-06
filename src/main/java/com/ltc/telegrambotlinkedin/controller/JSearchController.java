package com.ltc.telegrambotlinkedin.controller;

import com.ltc.telegrambotlinkedin.dto.jSearchDto.Job;
import com.ltc.telegrambotlinkedin.service.JSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/jSearch")
public class JSearchController {
    private final JSearchService jSearchService;

    @GetMapping("/getJob")
    public ArrayList<Job> getJob (@RequestParam(name = "query") String query, @RequestParam (name= "remote_jobs_only") Boolean isRemote) {
        return jSearchService.getAllJobs(query, isRemote);
    }
}