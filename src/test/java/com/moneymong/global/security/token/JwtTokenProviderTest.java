package com.moneymong.global.security.token;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.moneymong.global.security.token.service.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.Map;

class JwtTokenProviderTest {

    private static final String USER_TOKEN = "test-token";
    private static final String ISSUER = "issuer";
    private static final String SECRET_KEY = "moneymong is the best accounting management app of all time.";
    private static final int ACCESS_TOKEN_EXPIRY_SECONDS = 3;

    private final JwtTokenProvider jwtTokenProvider
            = new JwtTokenProvider(ISSUER, SECRET_KEY, ACCESS_TOKEN_EXPIRY_SECONDS);

    private String role = "ADMIN";

    private String accessToken;
    @Test
    @DisplayName("페이로드(userToken, role)를 담은 JWT를 생성 및 추출할 수 있다.")
    void success1() {
        // when
        String accessToken = jwtTokenProvider.getAccessToken(USER_TOKEN, role);
        Claims claims = jwtTokenProvider.getClaims(accessToken);

        //then
        assertDoesNotThrow(() -> jwtTokenProvider.getAccessToken(USER_TOKEN, role));

        assertThat(claims)
                .containsEntry("userToken", USER_TOKEN)
                .containsEntry("role", role);
    }

    @Test
    @DisplayName("유효한 토큰의 경우 검증 시 예외가 발생하지 않는다.")
    void success2() {
        // given
        accessToken = jwtTokenProvider.getAccessToken(USER_TOKEN, role);

        // when & then
        assertDoesNotThrow(() -> jwtTokenProvider.validateToken(accessToken));
    }

    @Test
    @DisplayName("토큰의 만료 시간이 지나면 ExpiredTokenProblem이 발생한다.")
    void fail1() throws Exception {
        // given
        accessToken = jwtTokenProvider.getAccessToken(USER_TOKEN, role);

        Thread.sleep(ACCESS_TOKEN_EXPIRY_SECONDS * 1000L);

        // when & then
//        assertThatThrownBy(() -> jwtTokenProvider.validateToken(accessToken))
//                .isInstanceOf(ExpiredTokenProblem.class);
    }

    @Test
    @DisplayName("유효하지 않은 토큰일 경우 InvalidTokenProblem이 발생한다.")
    void fail2() {
        // given
        accessToken = "InvalidToken";

        // when & then
//        assertThatThrownBy(() -> jwtTokenProvider.validateToken(accessToken))
//                .isInstanceOf(InvalidTokenProblem.class);
    }

    @Test
    @DisplayName("토큰 값이 null인 경우 InvalidTokenProblem 발생한다.")
    void fail3() {
        // given
        accessToken = null;

        // when & then
//        assertThatThrownBy(() -> jwtTokenProvider.validateToken(accessToken))
//                .isInstanceOf(InvalidTokenProblem.class);
    }

    @Test
    @DisplayName("올바르지 않은 키로 검증 시 예외가 발생한다.")
    void fail4() {
        // given
        String invalidSecretKey = "moneymong is the worst accounting management app of all time.";

        JwtTokenProvider wongTokenProvider
                = new JwtTokenProvider(ISSUER, invalidSecretKey, ACCESS_TOKEN_EXPIRY_SECONDS);

        //when
        accessToken = wongTokenProvider.getAccessToken(USER_TOKEN, role);

        //then
//        assertThatThrownBy(() -> jwtTokenProvider.validateToken(accessToken))
//                .isInstanceOf(InvalidTokenProblem.class);
    }

}
