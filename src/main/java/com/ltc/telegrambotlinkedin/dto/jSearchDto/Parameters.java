package com.ltc.telegrambotlinkedin.dto.jSearchDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Parameters {
    @JsonProperty("query")
    public String query;
    @JsonProperty("page")
    public int page;
    @JsonProperty("num_pages")
    public int numPages;
}
