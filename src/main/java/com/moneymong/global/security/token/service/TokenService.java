package com.moneymong.global.security.token.service;

import com.moneymong.global.security.oauth.dto.AuthUserInfo;
import com.moneymong.global.security.token.dto.Tokens;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Tokens createTokens(AuthUserInfo authUserInfo) {
      
        String accessToken = createAccessToken(authUserInfo.getUserToken(), "role");
        String refreshToken = createRefreshToken();

        return new Tokens(accessToken, refreshToken);
    }

    private String createAccessToken(String userToken, String role) {
        return jwtTokenProvider.getAccessToken(userToken, role);
    }

    private String createRefreshToken() {
        return "";
    }

}
