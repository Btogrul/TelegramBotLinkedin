package com.ltc.telegrambotlinkedin.dto.jSearchDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class JSearchRoot {
    @JsonProperty("status")
    public String status;
    @JsonProperty("data")
    public ArrayList<Job> data;
}