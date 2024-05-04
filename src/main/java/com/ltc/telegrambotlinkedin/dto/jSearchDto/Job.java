package com.ltc.telegrambotlinkedin.dto.jSearchDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class Job {
    @JsonProperty("job_id")
    private String jobId;
    @JsonProperty("employer_name")
    private String employerName;
    @JsonProperty("employer_logo")
    private String employerLogo;
    @JsonProperty("employer_website")
    private String employerWebsite;
    @JsonProperty("employer_company_type")
    private String employerCompanyType;
    @JsonProperty("job_publisher")
    private String jobPublisher;
    @JsonProperty("job_employment_type")
    private String jobEmploymentType;
    @JsonProperty("job_title")
    private String jobTitle;
    @JsonProperty("job_apply_link")
    private String jobApplyLink;
    @JsonProperty("job_apply_is_direct")
    private boolean jobApplyIsDirect;
    @JsonProperty("job_apply_quality_score")
    private double jobApplyQualityScore;
    @JsonProperty("apply_options")
    private ArrayList<ApplyOption> applyOptions;
    @JsonProperty("job_description")
    private String jobDescription;
    @JsonProperty("job_is_remote")
    private boolean jobIsRemote;
    @JsonProperty("job_posted_at_timestamp")
    private int jobPostedAtTimestamp;
    @JsonProperty("job_posted_at_datetime_utc")
    private Date jobPostedAtDatetimeUtc;
    private String job_city;
    private String job_state;
    private String job_country;
    private double job_latitude;
    private double job_longitude;
    private ArrayList<String> job_benefits;
    private String job_google_link;
    private Date job_offer_expiration_datetime_utc;
    private int job_offer_expiration_timestamp;
    private JobRequiredExperience job_required_experience;
    private ArrayList<String> job_required_skills;
    private JobRequiredEducation job_required_education;
    private boolean job_experience_in_place_of_education;
    private int job_min_salary;
    private int job_max_salary;
    private String job_salary_currency;
    private String job_salary_period;
    private JobHighlights job_highlights;
    private String job_job_title;
    private String job_posting_language;
    private String job_onet_soc;
    private String job_onet_job_zone;
    private String job_naics_code;
    private String job_naics_name;
    private ArrayList<String> job_occupational_categories;
}
