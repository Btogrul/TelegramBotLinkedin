package com.ltc.telegrambotlinkedin.enums;

public enum UserStage {
    CREATED,
    ENTERING_JOB,
    ENTERING_SKILLS,
    CONFIRM_JOB_TITLE,
    PROCESSED;

    @Override
    public String toString() {
        return this.name();
    }
}
