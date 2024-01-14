package com.moneymong.domain.agency.entity.enums;

public enum AgencyUserRole {
    STAFF,
    MEMBER,
    BLOCKED;

    public static boolean isStaffUser(AgencyUserRole role) {
        return role == STAFF;
    }
    public static boolean isMemberUser(AgencyUserRole role) {
        return role == MEMBER;
    }
    public static boolean isBlockedUser(AgencyUserRole role) {
        return role == BLOCKED;
    }
}
