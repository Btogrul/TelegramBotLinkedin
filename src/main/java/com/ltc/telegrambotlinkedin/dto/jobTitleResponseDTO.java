package com.ltc.telegrambotlinkedin.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class jobTitleResponseDTO {
    Long Id;
    String jobTitle;
}
