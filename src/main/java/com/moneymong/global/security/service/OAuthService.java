package com.moneymong.global.security.service;

import com.moneymong.domain.user.api.request.LoginRequest;
import com.moneymong.domain.user.api.request.UserDeleteRequest;
import com.moneymong.global.security.oauth.dto.OAuthUserDataRequest;
import com.moneymong.global.security.oauth.dto.OAuthUserDataResponse;
import com.moneymong.global.security.oauth.handler.OAuthAuthenticationHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OAuthService {

    private final Map<OAuthProvider, OAuthAuthenticationHandler> oAuthAuthenticationHandlers;

    public OAuthService(List<OAuthAuthenticationHandler> oAuthAuthenticationHandlers) {
        this.oAuthAuthenticationHandlers = oAuthAuthenticationHandlers.stream().collect(
                Collectors.toConcurrentMap(OAuthAuthenticationHandler::getAuthProvider, Function.identity())
        );
    }

    public OAuthUserDataResponse login(LoginRequest loginRequest) {
        OAuthProvider oAuthProvider = OAuthProvider.get(loginRequest.getProvider());

        OAuthAuthenticationHandler oAuthHandler = this.oAuthAuthenticationHandlers.get(oAuthProvider);

        OAuthUserDataRequest request = new OAuthUserDataRequest(
                loginRequest.getAccessToken(),
                loginRequest.getCode(),
                loginRequest.getName()
        );

        return oAuthHandler.getOAuthUserData(request);
    }

    public void revoke(UserDeleteRequest deleteRequest) {
        OAuthProvider oAuthProvider = OAuthProvider.get(deleteRequest.getProvider());
        OAuthAuthenticationHandler oAuthHandler = this.oAuthAuthenticationHandlers.get(oAuthProvider);

        oAuthHandler.unlink(deleteRequest.getToken());
    }
}
