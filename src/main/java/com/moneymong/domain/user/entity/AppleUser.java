package com.moneymong.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Table(name = "apple_users")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class AppleUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String appleRefreshToken;

    public static AppleUser of(Long userId, String appleRefreshToken) {
        return AppleUser.builder()
                .userId(userId)
                .appleRefreshToken(appleRefreshToken)
                .build();
    }
}
