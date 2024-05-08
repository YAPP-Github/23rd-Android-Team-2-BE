package com.moneymong.global.security.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthUserDataRequest {
    private String accessToken;
    private String name;
}
