package com.moneymong.global.security.oauth.handler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moneymong.domain.user.entity.AppleUser;
import com.moneymong.domain.user.repository.AppleUserRepository;
import com.moneymong.global.exception.enums.ErrorCode;
import com.moneymong.global.security.oauth.dto.AppleUserData;
import com.moneymong.global.security.oauth.dto.OAuthUserDataRequest;
import com.moneymong.global.security.oauth.dto.OAuthUserDataResponse;
import com.moneymong.global.security.oauth.exception.HttpClientException;
import com.moneymong.global.security.service.OAuthProvider;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.auth0.jwt.interfaces.Claim;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.PrivateKey;
import java.security.Security;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppleService implements OAuthAuthenticationHandler {

    private final RestTemplate restTemplate;
    private final AppleUserRepository appleUserRepository;

    @Value("${spring.security.oauth2.apple.host}")
    private String host;

    @Value("${spring.security.oauth2.apple.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.apple.key-id}")
    private String keyId;

    @Value("${spring.security.oauth2.apple.team-id}")
    private String teamId;

    @Value("${spring.security.oauth2.apple.private-key}")
    private String privateKey;

    @Override
    public OAuthProvider getAuthProvider() {
        return OAuthProvider.APPLE;
    }

    @Override
    public OAuthUserDataResponse getOAuthUserData(OAuthUserDataRequest request) {
        if (request.getCode() == null) {
            return decodePayload(request.getAccessToken(), request.getName(), null);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> httpRequest = new HttpEntity<>(null, httpHeaders);

        MultiValueMap<String, String> parameterMap = new LinkedMultiValueMap<>();
        parameterMap.add("client_id", clientId);
        parameterMap.add("client_secret", createClientSecret());
        parameterMap.add("grant_type", "authorization_code");
        parameterMap.add("code", request.getCode());

        URI uri = UriComponentsBuilder
                .fromUriString(host + "/auth/oauth2/v2/token")
                .queryParams(parameterMap)
                .build()
                .toUri();

        try {
            AppleUserData userData = restTemplate.postForObject(
                    uri,
                    httpRequest,
                    AppleUserData.class
            );

            assert userData != null;

            String refreshToken = userData.getRefreshToken();
            String idToken = userData.getIdToken();

            log.info("[AppleService] refreshToken = {}", refreshToken);
            return decodePayload(idToken, request.getName(), refreshToken);
        } catch (RestClientException e) {
            log.info("[AppleService] error message = {}", e.getMessage());
            log.warn("[AppleService] failed to get OAuth User Data = {}", request.getAccessToken());
            throw new HttpClientException(ErrorCode.HTTP_CLIENT_REQUEST_FAILED);
        }
    }

    @Override
    public void unlink(Long userId) {
        AppleUser appleUser = appleUserRepository.findAppleUserByUserId(userId)
                .orElseThrow(() -> new RuntimeException());

        String refreshToken = appleUser.getAppleRefreshToken();
        String clientSecret = createClientSecret();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("token", refreshToken);
        params.add("token_type_hint", "refresh_token");

        URI uri = UriComponentsBuilder
                .fromUriString(host + "/auth/oauth2/v2/revoke")
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        try {
            restTemplate.postForEntity(uri, httpEntity, AppleUserData.class);
        } catch (RestClientException e) {
            throw new HttpClientException(ErrorCode.HTTP_CLIENT_REQUEST_FAILED);
        }
    }

    private String createClientSecret() {
        ZonedDateTime expiration = ZonedDateTime.now().plusMinutes(5);

        return Jwts.builder()
                .setHeaderParam(JwsHeader.KEY_ID, keyId)
                .setIssuer(teamId)
                .setAudience(host)
                .setSubject(clientId)
                .setExpiration(Date.from(expiration.withZoneSameInstant(ZoneId.systemDefault()).toInstant()))
                .setIssuedAt(new Date())
                .signWith(getPrivateKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    private PrivateKey getPrivateKey() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);

            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(privateKeyBytes);
            return converter.getPrivateKey(privateKeyInfo);
        } catch (Exception e) {
            throw new RuntimeException("Error converting private key from String", e);
        }
    }

    private OAuthUserDataResponse decodePayload(String idToken, String nickname, String refreshToken) {
        try {
            DecodedJWT decoded = JWT.decode(idToken);
            Map<String, Claim> claims = decoded.getClaims();

            log.info("[AppleService] claims = {}", claims.keySet());
            String providerUid = decoded.getSubject();
            String email = claims.get("sub").asString();

            log.info("[AppleService] email = {}", email);
            log.info("[AppleService] nickname = {}", nickname);
            return OAuthUserDataResponse.builder()
                    .provider(getAuthProvider().toString())
                    .oauthId(providerUid)
                    .email(email)
                    .nickname(nickname)
                    .appleRefreshToken(refreshToken)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error decoding payload", e);
        }
    }
}
