package com.moneymong.global.security.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuthUserDataResponse {
    private String provider;
    private String oauthId;
    private String email;
    private String nickname;
    private String appleRefreshToken;
}
