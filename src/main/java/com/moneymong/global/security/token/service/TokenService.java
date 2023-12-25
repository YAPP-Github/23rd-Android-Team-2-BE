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
import lombok.RequiredArgsConstructor;
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
        Long userId = authUserInfo.getUserId();
        String role = authUserInfo.getRole();

        String accessToken = createAccessToken(userId, role);
        String refreshToken = createRefreshToken(userId, role);

        return new Tokens(accessToken, refreshToken);
    }

    private String createAccessToken(Long userId, String role) {
        return jwtTokenProvider.getAccessToken(userId, role);
    }

    private String createRefreshToken(Long userId, String role) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .userId(userId)
                .role(role)
                .build();

        return refreshTokenRepository.save(refreshToken).getToken();
    }

    @Transactional(readOnly = true)
    public String getAccessTokensByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findById(refreshToken)
                .map(token -> createAccessToken(token.getUserId(), token.getRole()))
                .orElseThrow(() -> new RefreshTokenNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
    }

    public JwtAuthenticationToken getAuthenticationByAccessToken(String accessToken) {
        jwtTokenProvider.validateToken(accessToken);

        Claims claims = jwtTokenProvider.getClaims(accessToken);

        Long id = claims.get("userId", Long.class);
        String role = claims.get("role", String.class);

        JwtAuthentication principal = new JwtAuthentication(id, accessToken);
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        return new JwtAuthenticationToken(principal, null, authorities);
    }

    @Transactional
    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.findById(refreshToken)
                .ifPresent(refreshTokenRepository::delete);
    }

}
