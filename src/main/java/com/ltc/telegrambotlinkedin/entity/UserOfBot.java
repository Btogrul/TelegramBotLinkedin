package com.ltc.telegrambotlinkedin.entity;

import com.ltc.telegrambotlinkedin.enums.UserStage;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_of_bot")
public class UserOfBot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(unique = true, name = "chat_id")
    private long chatId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "job_title")
    private String jobTitle;
    @Column(name = "stage")
    private UserStage stage;
    @Column(name = "user_location")
    private String userLocation;
    @Column(name = "user_locale")
    private Locale userLocale;
    @Column(name = "only_remote")
    private boolean onlyRemote;

    @CreationTimestamp
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "update_date")
    private Date updateDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Skill> skillSet;
}