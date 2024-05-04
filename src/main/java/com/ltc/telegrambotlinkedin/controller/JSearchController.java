package com.ltc.telegrambotlinkedin.controller;

import com.ltc.telegrambotlinkedin.dto.jSearchDto.JSearchRoot;
import com.ltc.telegrambotlinkedin.dto.userDTO.UserForJSearchDTO;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import com.ltc.telegrambotlinkedin.service.JSearchService;
import jdk.dynalink.linker.LinkerServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Queue;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job")
public class JSearchController {
    private final JSearchService jsearchSvc;

    @GetMapping("/getJob")
    public JSearchRoot getJob (@RequestParam(name = "query") String query) {
        return jsearchSvc.getJobSearchResults(query);
    }

    @GetMapping("/getUsers")
    public Queue<UserForJSearchDTO> getProcessed () {
        return jsearchSvc.retrieveProcessedUsers();
    }
}
