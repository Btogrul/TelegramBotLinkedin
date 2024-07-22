package com.ltc.telegrambotlinkedin.dto.jSearchDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApplyOption {
    @JsonProperty("publisher")
    public String publisher;
    @JsonProperty("apply_link")
    public String applyLink;
    @JsonProperty("is_direct")
    public boolean isDirect;
}
