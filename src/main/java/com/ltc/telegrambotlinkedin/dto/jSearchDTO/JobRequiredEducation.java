package com.ltc.telegrambotlinkedin.dto.jSearchDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JobRequiredEducation {
    @JsonProperty("postgraduate_degree")
    public boolean postgraduateDegree;
    @JsonProperty("professional_certification")
    public boolean professionalCertification;
    @JsonProperty("high_school")
    public boolean highSchool;
    @JsonProperty("associates_degree")
    public boolean associatesDegree;
    @JsonProperty("bachelors_degree")
    public boolean bachelorsDegree;
    @JsonProperty("degree_mentioned")
    public boolean degreeMentioned;
    @JsonProperty("degree_preferred")
    public boolean degreePreferred;
    @JsonProperty("professional_certification_mentioned")
    public boolean professionalCertificationMentioned;
}
