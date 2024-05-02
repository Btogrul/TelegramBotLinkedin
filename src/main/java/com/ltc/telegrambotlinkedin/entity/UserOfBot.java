package com.ltc.telegrambotlinkedin.entity;

import com.ltc.telegrambotlinkedin.enums.UserStage;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_of_bot")
public class UserOfBot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private int chatId;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private UserStage stage;
    private String location;


    @CreationTimestamp
    private Date creationDate;

    @UpdateTimestamp
    private Date updateDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Skill> skillSet;
}