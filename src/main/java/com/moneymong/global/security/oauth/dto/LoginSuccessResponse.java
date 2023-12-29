package com.moneymong.global.security.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginSuccessResponse {
    private String accessToken;
    private String refreshToken;
    private Boolean loginSuccess;
    private Boolean schoolInfoExist;

    public static LoginSuccessResponse from(String accessToken, String refreshToken, Boolean loginSuccess, Boolean schoolInfoExist) {
        return LoginSuccessResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .loginSuccess(loginSuccess)
                .schoolInfoExist(schoolInfoExist)
                .build();
    }
}
