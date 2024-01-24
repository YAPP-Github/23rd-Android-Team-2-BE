package com.moneymong.global.security.token.entity;

import com.moneymong.global.domain.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class RefreshToken extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private ZonedDateTime expiredAt;

    @Builder
    private RefreshToken(String token, Long userId, String role, ZonedDateTime expiredAt) {
        this.token = token;
        this.userId = userId;
        this.role = role;
        this.expiredAt = expiredAt;
    }

    public void renew(String token, ZonedDateTime expiredAt) {
        this.token = token;
        this.expiredAt = expiredAt;
    }
}
