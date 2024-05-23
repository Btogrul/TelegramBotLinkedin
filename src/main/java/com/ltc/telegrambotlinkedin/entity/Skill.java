package com.ltc.telegrambotlinkedin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String skillName;

    @Override
    public String toString() {
        return skillName;
    }
}
