package com.ltc.telegrambotlinkedin.dto.jSearchDto;

import lombok.Data;

@Data
public class JobRequiredExperience {
    public boolean no_experience_required;
    public int required_experience_in_months;
    public boolean experience_mentioned;
    public boolean experience_preferred;
}
