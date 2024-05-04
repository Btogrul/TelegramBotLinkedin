package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.JSearchClient;
import com.ltc.telegrambotlinkedin.dto.jSearchDto.JSearchRoot;
import com.ltc.telegrambotlinkedin.dto.userDTO.UserForJSearchDTO;
import com.ltc.telegrambotlinkedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;

@Slf4j
@RequiredArgsConstructor
@Service
public class JSearchService {
    @Value("${jSearchKey}")
    private String jSearchKey;
    @Value("${jSearchHost}")
    private String jSearchHost;

    private final JSearchClient jSearchClient;
    private final UserRepository userRepo;
    private final Queue<UserForJSearchDTO> processedUsers = new ArrayDeque<>();
  
    public JSearchRoot getJobSearchResults(String query) {
        return jSearchClient.getSearch(jSearchHost, jSearchKey, query, 1, 10);
    }

    public Queue<UserForJSearchDTO> retrieveProcessedUsers () {
        processedUsers.addAll(userRepo.findProcessedUsers());
        return processedUsers;
    }
}
