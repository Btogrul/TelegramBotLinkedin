package com.ltc.telegrambotlinkedin.dto.jSearchDto;

import lombok.Data;

@Data
public class Parameters {
    public String query;
    public int page;
    public int num_pages;
}
