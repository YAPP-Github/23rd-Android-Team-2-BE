package com.moneymong.global.security.token.entity;

import com.moneymong.global.domain.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import static lombok.AccessLevel.PROTECTED;

@RedisHash(value = "refreshToken")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class RefreshToken extends TimeBaseEntity {


    @Id
    private Long userId;

    @Indexed
    private String token;

    private String role;

    @TimeToLive
    private long expiredAt;

    @Builder
    private RefreshToken(String token, Long userId, String role, long expiredAt) {
        this.token = token;
        this.userId = userId;
        this.role = role;
        this.expiredAt = expiredAt;
    }

    public void renew(String token, long expiredAt) {
        this.token = token;
        this.expiredAt = expiredAt;
    }
}
