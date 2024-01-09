package com.moneymong.global.security.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserData {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    static class KakaoAccount {
        private String email;
        private KakaoProfile profile;
    }

    @Getter
    @NoArgsConstructor
    static class KakaoProfile {
        private String nickname;
    }

    public String getEmail() {
        return kakaoAccount.getEmail();
    }

    public String getNickname() {
        return kakaoAccount.getProfile().getNickname();
    }
}
