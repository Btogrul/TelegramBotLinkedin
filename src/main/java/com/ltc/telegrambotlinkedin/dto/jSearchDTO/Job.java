package com.ltc.telegrambotlinkedin.dto.jSearchDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Job {
    @JsonProperty("job_id")
    private String jobId;
    @JsonProperty("job_title")
    private String jobTitle;
    @JsonProperty("job_apply_link")
    private String jobApplyLink;
    @JsonProperty("job_description")
    private String jobDescription;
    @JsonProperty("job_is_remote")
    private boolean jobIsRemote;
}