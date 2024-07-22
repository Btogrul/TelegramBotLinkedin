package com.ltc.telegrambotlinkedin.dto.geminiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class GeminiResponse {
    @JsonProperty
    private List<Candidate> candidates;
    @JsonProperty
    private UsageMetadata usageMetadata;

    @Data
    static class UsageMetadata {
        private int promptTokenCount;
        private int candidatesTokenCount;
        private int totalTokenCount;
    }


    static class Candidate{
        @JsonProperty
        private ResponseContent content;
        @JsonProperty
        private String finishReason;
        @JsonProperty
        private int index;
        @JsonProperty
        private List<SafetyRating> safetyRatings;


        static class ResponseContent {
            @JsonProperty
            private List<ResponsePart> parts;
            @JsonProperty
            private String role;

            public static class ResponsePart {
                @JsonProperty
                private String text;
            }
        }


        static class SafetyRating {
            @JsonProperty
            private String category;
            @JsonProperty
            private String probability;
        }
    }

    public String getResponse () {
        return candidates.getFirst().content.parts.getFirst().text;
    }
}