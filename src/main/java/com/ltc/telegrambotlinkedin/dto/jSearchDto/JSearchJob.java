package com.ltc.telegrambotlinkedin.dto.jSearchDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JSearchJob {
    @JsonProperty("title")
    private String title;
    @JsonProperty("company")
    private String company;
    @JsonProperty("location")
    private String location;
    @JsonProperty("description")
    private String description;
    @JsonProperty("url")
    private String url;
}
