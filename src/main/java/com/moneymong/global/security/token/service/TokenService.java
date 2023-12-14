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
        //TODO OAuth 인증 후 각 소속마다 권한을 조회한다.
        String accessToken = createAccessToken(authUserInfo.getUserToken(), new HashMap<>());
        String refreshToken = createRefreshToken();

        return new Tokens(accessToken, refreshToken);
    }

    private String createAccessToken(String userToken, Map<Long, String> roles) {
        return jwtTokenProvider.getAccessToken(userToken, roles);
    }

    private String createRefreshToken() {
        return "";
    }

}
