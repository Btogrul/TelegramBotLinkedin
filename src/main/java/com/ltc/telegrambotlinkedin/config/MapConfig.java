package com.ltc.telegrambotlinkedin.config;

import com.ltc.telegrambotlinkedin.dto.userDTO.UserRequestDTO;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(UserRequestDTO.class, UserOfBot.class)
                    .addMapping(src -> null, UserOfBot::setId);
        return modelMapper;
    }
}
