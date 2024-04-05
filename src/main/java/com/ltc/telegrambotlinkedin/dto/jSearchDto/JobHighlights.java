package com.ltc.telegrambotlinkedin.dto.jSearchDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class JobHighlights {
    @JsonProperty("Qualifications")
    public ArrayList<String> qualifications;
    @JsonProperty("Benefits")
    public ArrayList<String> benefits;
    @JsonProperty("Responsibilities")
    public ArrayList<String> responsibilities;
}
