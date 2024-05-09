package com.moneymong.global.security.oauth.handler;

import com.moneymong.global.exception.enums.ErrorCode;
import com.moneymong.global.security.oauth.dto.KakaoUserData;
import com.moneymong.global.security.oauth.dto.OAuthUserDataRequest;
import com.moneymong.global.security.oauth.dto.OAuthUserDataResponse;
import com.moneymong.global.security.oauth.exception.HttpClientException;
import com.moneymong.global.security.service.OAuthProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoService implements OAuthAuthenticationHandler {

    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.kakao.host}")
    private String host;

    @Override
    public OAuthProvider getAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public OAuthUserDataResponse getOAuthUserData(OAuthUserDataRequest request) {
        String url = host + "/v2/user/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.add("Authorization", "Bearer " + request.getAccessToken());

        HttpEntity<?> httpRequest = new HttpEntity<>(null, httpHeaders);

        try {
            ResponseEntity<KakaoUserData> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    httpRequest,
                    KakaoUserData.class
            );
            assert response.getBody() != null;

            KakaoUserData userData = response.getBody();
            return OAuthUserDataResponse.builder()
                    .provider(getAuthProvider().toString())
                    .oauthId(userData.getId().toString())
                    .email(userData.getEmail())
                    .nickname(userData.getNickname())
                    .build();

        } catch (RestClientException e) {
            log.warn("[KakaoService] failed to get OAuth User Data = {}", request.getAccessToken());

            if (e instanceof RestClientResponseException) {
                throw new HttpClientException(ErrorCode.INVALID_OAUTH_TOKEN);
            }

            throw new HttpClientException(ErrorCode.HTTP_CLIENT_REQUEST_FAILED);
        }
    }

    @Override
    public void unlink(String token) {

    }
}
