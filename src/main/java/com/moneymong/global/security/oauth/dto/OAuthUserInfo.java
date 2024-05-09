package com.moneymong.global.security.oauth.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuthUserInfo {
    private String provider;
    private String oauthId;
    private String nickname;
    private String email;
    private String appleRefreshToken;

    public static OAuthUserInfo from(OAuthUserDataResponse oAuthUserDataResponse) {
        return OAuthUserInfo.builder()
                .provider(oAuthUserDataResponse.getProvider())
                .oauthId(oAuthUserDataResponse.getOauthId())
                .nickname(oAuthUserDataResponse.getNickname())
                .email(oAuthUserDataResponse.getEmail())
                .appleRefreshToken(oAuthUserDataResponse.getAppleRefreshToken())
                .build();
    }
}
