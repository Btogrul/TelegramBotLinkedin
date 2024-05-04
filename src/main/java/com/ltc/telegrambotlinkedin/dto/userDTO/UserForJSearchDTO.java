package com.ltc.telegrambotlinkedin.dto.userDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserForJSearchDTO {
    @JsonProperty("id")
    private long userId;
    @JsonProperty("job_title")
    private String jobTitle;
    @JsonProperty("user_location")
    private String location;
    @JsonProperty("update_date")
    private Date updateDate;
}
