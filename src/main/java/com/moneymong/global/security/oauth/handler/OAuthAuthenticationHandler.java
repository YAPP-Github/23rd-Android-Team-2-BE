package com.moneymong.global.security.oauth.handler;

import com.moneymong.global.security.oauth.dto.OAuthUserDataRequest;
import com.moneymong.global.security.oauth.dto.OAuthUserDataResponse;
import com.moneymong.global.security.service.OAuthProvider;

public interface OAuthAuthenticationHandler {
    OAuthProvider getAuthProvider();

    OAuthUserDataResponse getOAuthUserData(OAuthUserDataRequest request);
}
