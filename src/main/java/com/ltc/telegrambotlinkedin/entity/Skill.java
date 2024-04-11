package com.ltc.telegrambotlinkedin.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String skillName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private List<UserOfBot> users;
}