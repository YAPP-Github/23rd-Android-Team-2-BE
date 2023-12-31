package com.moneymong.domain.agency.entity.enums;

public enum AgencyUserRole {
    STAFF,
    MEMBER,
    BLOCKED;

    public static boolean isStaff(AgencyUserRole role) {
        return role == STAFF;
    }
}
