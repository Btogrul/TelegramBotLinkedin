package com.ltc.telegrambotlinkedin.config;

import com.ltc.telegrambotlinkedin.dto.gpt.request.Message;
import com.ltc.telegrambotlinkedin.dto.gpt.request.MessageRoot;
import com.ltc.telegrambotlinkedin.dto.userDTO.UserRequestDTO;
import com.ltc.telegrambotlinkedin.entity.Skill;
import com.ltc.telegrambotlinkedin.entity.UserOfBot;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MapConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(UserRequestDTO.class, UserOfBot.class)
                    .addMapping(src -> null, UserOfBot::setId);

        modelMapper.createTypeMap(String.class, Skill.class)
                .setConverter(context -> {
            Skill skill = new Skill();
            skill.setSkillName(context.getSource());
            return skill;
        });

        modelMapper.createTypeMap(String.class, MessageRoot.class)
                .setConverter(content -> {
                    var message = new Message();
                    message.setContent(content.getSource());
                    var root = new MessageRoot();
                    root.setMessages(new ArrayList<>(List.of(message)));
                    return root;
                });
        return modelMapper;
    }
}
