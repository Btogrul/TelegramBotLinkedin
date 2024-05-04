package com.ltc.telegrambotlinkedin.dto.jSearchDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class JSearchRoot {
    @JsonProperty("status")
    public String status;
    @JsonProperty("request_id")
    public String requestId;
    @JsonProperty("parameters")
    public Parameters parameters;
    @JsonProperty("data")
    public ArrayList<Job> data;
}
