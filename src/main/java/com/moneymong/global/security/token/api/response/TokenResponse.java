package com.moneymong.global.security.token.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;

    /**
     * p2. 보통 RefreshToken 도 같이 내려줍니다.
     */
}
