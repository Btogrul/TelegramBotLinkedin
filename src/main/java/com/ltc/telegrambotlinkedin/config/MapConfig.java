package com.ltc.telegrambotlinkedin.config;

import com.ltc.telegrambotlinkedin.dto.userDTO.UserRequest;
import com.ltc.telegrambotlinkedin.entity.Skill;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(UserRequest.class, UserOfBot.class)
                    .addMapping(src -> null, UserOfBot::setId);

        modelMapper.createTypeMap(String.class, Skill.class)
                .setConverter(context -> {
            Skill skill = new Skill();
            skill.setSkillName(context.getSource());
            return skill;
        });
        return modelMapper;
    }
}
