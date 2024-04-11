package com.ltc.telegrambotlinkedin.dto.userDTO;

//import com.ltc.telegrambotlinkedin.entity.Skill;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {
    private int chatId;
    private String first_name;
    private String last_name;
    private String jobTitle;
//    @ManyToMany
//    private List<Skill> skillSet;
}
