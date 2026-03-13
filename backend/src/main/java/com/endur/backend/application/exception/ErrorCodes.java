package com.endur.backend.application.exception;

public final class ErrorCodes {

    public static final String INVITE_NOT_FOUND = "INVITE_NOT_FOUND";
    public static final String INVITE_NOT_AVAILABLE = "INVITE_NOT_AVAILABLE";
    public static final String INVITE_ALREADY_USED = "INVITE_ALREADY_USED";
    public static final String INVITE_EXPIRED = "INVITE_EXPIRED";
    public static final String COACH_NOT_ACCEPTING_NEW_ATHLETES = "COACH_NOT_ACCEPTING_NEW_ATHLETES";
    public static final String ACTIVE_LINK_ALREADY_EXISTS = "ACTIVE_LINK_ALREADY_EXISTS";
    public static final String PRIMARY_LINK_ALREADY_EXISTS = "PRIMARY_LINK_ALREADY_EXISTS";
    public static final String ATHLETE_PROFILE_NOT_FOUND = "ATHLETE_PROFILE_NOT_FOUND";
    public static final String PROGRAM_TYPE_REQUIRED = "PROGRAM_TYPE_REQUIRED";
    public static final String DISCIPLINES_REQUIRED = "DISCIPLINES_REQUIRED";
    public static final String TRIATHLON_DISCIPLINES_REQUIRED = "TRIATHLON_DISCIPLINES_REQUIRED";

    private ErrorCodes() {
    }
}
