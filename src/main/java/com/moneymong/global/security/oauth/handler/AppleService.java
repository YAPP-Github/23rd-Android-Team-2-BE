package com.moneymong.global.security.oauth.handler;

import com.moneymong.global.security.oauth.dto.OAuthUserDataRequest;
import com.moneymong.global.security.oauth.dto.OAuthUserDataResponse;
import com.moneymong.global.security.service.OAuthProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppleService implements OAuthAuthenticationHandler {

    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.apple.host}")
    private String host;

    @Override
    public OAuthProvider getAuthProvider() {
        return OAuthProvider.APPLE;
    }

    @Override
    public OAuthUserDataResponse getOAuthUserData(OAuthUserDataRequest request) {
        return null;
    }
}
