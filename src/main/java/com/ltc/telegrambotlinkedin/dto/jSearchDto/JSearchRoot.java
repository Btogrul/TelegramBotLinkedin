package com.ltc.telegrambotlinkedin.dto.jSearchDto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class JSearchRoot {
    public String status;
    public String request_id;
    public Parameters parameters;
    public ArrayList<Datum> data;
}
