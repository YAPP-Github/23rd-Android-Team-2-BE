package com.moneymong.global.security.token.entity;

import com.moneymong.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class RefreshToken extends BaseEntity {

    @Id
    private String token;

    @Column(nullable = false)
    private String userToken;

    @Column(nullable = false)
    private String role;

    @Builder
    private RefreshToken(String token, String userToken, String role) {
        this.token = token;
        this.userToken = userToken;
        this.role = role;
    }
}
