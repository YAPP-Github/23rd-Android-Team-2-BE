package com.moneymong.global.security.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuthUserInfo {
    private String provider;
    private String oauthId;
    private String nickname;
    private String profileImgUrl;

    public static OAuthUserInfo from(String provider, String oauthId, String nickname, String profileImgUrl) {
        return OAuthUserInfo.builder()
                .provider(provider)
                .oauthId(oauthId)
                .nickname(nickname)
                .profileImgUrl(profileImgUrl)
                .build();
    }
}
