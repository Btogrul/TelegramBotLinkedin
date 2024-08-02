package com.ltc.telegrambotlinkedin.dto.geminiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class GeminiResponse {
    @JsonProperty
    private List<Candidate> candidates;

    static class Candidate{
        @JsonProperty
        private ResponseContent content;

        static class ResponseContent {
            @JsonProperty
            private List<ResponsePart> parts;

            public static class ResponsePart {
                @JsonProperty
                private String text;
            }

        }

    }

    public String getResponse () {
        return candidates.getFirst().content.parts.getFirst().text;
    }

}