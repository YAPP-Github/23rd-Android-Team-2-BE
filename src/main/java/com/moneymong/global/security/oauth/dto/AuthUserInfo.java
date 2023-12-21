package com.moneymong.global.security.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthUserInfo {
    private final String userToken;
    private final String role;
}
