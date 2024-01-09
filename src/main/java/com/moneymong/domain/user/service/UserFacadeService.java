package com.moneymong.domain.user.service;

import com.moneymong.domain.user.api.request.LoginRequest;
import com.moneymong.global.security.oauth.dto.AuthUserInfo;
import com.moneymong.domain.user.api.response.LoginSuccessResponse;
import com.moneymong.global.security.oauth.dto.OAuthUserDataResponse;
import com.moneymong.global.security.oauth.dto.OAuthUserInfo;
import com.moneymong.global.security.service.OAuthService;
import com.moneymong.global.security.token.dto.Tokens;
import com.moneymong.global.security.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacadeService {
    private final UserService userService;
    private final TokenService tokenService;
    private final OAuthService oAuthService;
    private final UserUniversityService userUniversityService;

    public LoginSuccessResponse login(LoginRequest loginRequest) {
        OAuthUserDataResponse oAuthUserData = oAuthService.login(loginRequest);

        OAuthUserInfo oAuthUserInfo = OAuthUserInfo.from(oAuthUserData);

        AuthUserInfo registerResult = userService.getOrRegister(oAuthUserInfo);

        Tokens tokens = tokenService.createTokens(registerResult);

        boolean loginSuccess = true;
        boolean schoolInfoExists = userUniversityService.exists(registerResult.getUserId());

        return LoginSuccessResponse.of(tokens.getAccessToken(), tokens.getRefreshToken(), loginSuccess, schoolInfoExists);
    }
}
