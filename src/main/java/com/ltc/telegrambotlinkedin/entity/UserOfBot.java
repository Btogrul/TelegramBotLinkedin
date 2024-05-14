package com.ltc.telegrambotlinkedin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ltc.telegrambotlinkedin.dto.userDTO.UserForJSearchDTO;
import com.ltc.telegrambotlinkedin.enums.UserStage;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@SqlResultSetMapping(
        name = "UserForJSearchDTOMapping",
        classes = @ConstructorResult(
                targetClass = UserForJSearchDTO.class,
                columns = {
                        @ColumnResult(name = "userId", type = Long.class),
                        @ColumnResult(name = "jobTitle", type = String.class),
                        @ColumnResult(name = "userLocation", type = String.class),
                        @ColumnResult(name = "updateDate", type = Date.class)
                }
        )
)
@NamedNativeQuery(
        name = "UserForJSearchDTOQuerry",
        resultSetMapping = "UserForJSearchDTOMapping",
        query = "SELECT id as userId, job_title as jobTitle, user_location as userLocation, update_date as updateDate FROM user_of_bot u WHERE u.stage = 5"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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
    @Column(name = "language_code")
    private String languageCode;


    @CreationTimestamp
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "update_date")
    private Date updateDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Skill> skillSet;
}