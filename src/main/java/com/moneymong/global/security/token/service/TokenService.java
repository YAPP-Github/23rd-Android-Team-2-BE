package com.moneymong.global.security.token.service;

import com.moneymong.global.exception.enums.ErrorCode;
import com.moneymong.global.security.oauth.dto.AuthUserInfo;
import com.moneymong.global.security.token.dto.Tokens;
import com.moneymong.global.security.token.dto.jwt.JwtAuthentication;
import com.moneymong.global.security.token.dto.jwt.JwtAuthenticationToken;
import com.moneymong.global.security.token.entity.RefreshToken;
import com.moneymong.global.security.token.exception.RefreshTokenNotFoundException;
import com.moneymong.global.security.token.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.expiry-seconds.refresh-token}")
    private int refreshTokenExpireSeconds;

    @Transactional
    public Tokens createTokens(AuthUserInfo authUserInfo) {
        String userToken = authUserInfo.getUserToken();
        String role = authUserInfo.getRole();

        String accessToken = createAccessToken(userToken, role);
        String refreshToken = createRefreshToken(userToken, role);

        return new Tokens(accessToken, refreshToken);
    }

    private String createAccessToken(String userToken, String role) {
        return jwtTokenProvider.getAccessToken(userToken, role);
    }

    private String createRefreshToken(String userToken, String role) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .userToken(userToken)
                .role(role)
                .build();

        return refreshTokenRepository.save(refreshToken).getToken();
    }

    @Transactional(readOnly = true)
    public String getAccessTokensByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findById(refreshToken)
                .map(token -> createAccessToken(token.getUserToken(), token.getRole()))
                .orElseThrow(() -> new RefreshTokenNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
    }

    public JwtAuthenticationToken getAuthenticationByAccessToken(String accessToken) {
        jwtTokenProvider.validateToken(accessToken);

        Claims claims = jwtTokenProvider.getClaims(accessToken);

        String userToken = claims.get("userToken", String.class);
        String role = claims.get("role", String.class);

        JwtAuthentication principal = new JwtAuthentication(userToken, accessToken);
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        return new JwtAuthenticationToken(principal, null, authorities);
    }
}
