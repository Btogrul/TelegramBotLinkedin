package com.ltc.telegrambotlinkedin.dto.jSearchDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JobRequiredExperience {
    @JsonProperty("no_experience_required")
    public boolean noExperienceRequired;
    @JsonProperty("required_experience_in_months")
    public int requiredExperienceInMonths;
    @JsonProperty("experience_mentioned")
    public boolean experienceMentioned;
    @JsonProperty("experience_preferred")
    public boolean experiencePreferred;
}
