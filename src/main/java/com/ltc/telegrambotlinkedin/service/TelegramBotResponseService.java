package com.ltc.telegrambotlinkedin.service;

import com.ltc.telegrambotlinkedin.config.feign.TelegramBotClient;
import com.ltc.telegrambotlinkedin.dto.userDTO.UserRequestDTO;
import com.ltc.telegrambotlinkedin.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;

@Slf4j
@Data
@Service
@RequiredArgsConstructor
public class TelegramBotResponseService {
    private final TelegramBotClient bot;
    private final UserRepository userRepo;
    private final ModelMapper mpr;
    private int lastUpdateId;
    private final ArrayDeque<UserRequestDTO> queue = new ArrayDeque<>();

}
