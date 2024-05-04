package com.ltc.telegrambotlinkedin.dto.jSearchDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class JobHighlights {
    @JsonProperty("qualifications")
    public ArrayList<String> qualifications;
    @JsonProperty("benefits")
    public ArrayList<String> benefits;
    @JsonProperty("responsibilities")
    public ArrayList<String> responsibilities;
}
