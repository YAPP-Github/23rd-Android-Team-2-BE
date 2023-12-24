package com.moneymong.global.security.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthUserInfo {
    private final Long userId;
    private final String role;
}
