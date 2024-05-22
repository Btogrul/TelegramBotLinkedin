package com.ltc.telegrambotlinkedin.enums;

public enum UserStage {
    CREATED,
    ENTERING_TITLE,
    ENTERING_SKILLS,
    ENTERING_LOCATION,
    ENTERING_REMOTE,
    CONFIRM_SEARCH,
    PROCESSED;

    @Override
    public String toString() {
        return this.name();
    }
}
