package com.ltc.telegrambotlinkedin.dto.jSearchDto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class Datum {
    public String job_id;
    public String employer_name;
    public String employer_logo;
    public String employer_website;
    public String employer_company_type;
    public String job_publisher;
    public String job_employment_type;
    public String job_title;
    public String job_apply_link;
    public boolean job_apply_is_direct;
    public double job_apply_quality_score;
    public ArrayList<ApplyOption> apply_options;
    public String job_description;
    public boolean job_is_remote;
    public int job_posted_at_timestamp;
    public Date job_posted_at_datetime_utc;
    public String job_city;
    public String job_state;
    public String job_country;
    public double job_latitude;
    public double job_longitude;
    public ArrayList<String> job_benefits;
    public String job_google_link;
    public Date job_offer_expiration_datetime_utc;
    public int job_offer_expiration_timestamp;
    public JobRequiredExperience job_required_experience;
    public ArrayList<String> job_required_skills;
    public JobRequiredEducation job_required_education;
    public boolean job_experience_in_place_of_education;
    public int job_min_salary;
    public int job_max_salary;
    public String job_salary_currency;
    public String job_salary_period;
    public JobHighlights job_highlights;
    public Object job_job_title;
    public String job_posting_language;
    public String job_onet_soc;
    public String job_onet_job_zone;
    public String job_naics_code;
    public String job_naics_name;
    public ArrayList<String> job_occupational_categories;
}
