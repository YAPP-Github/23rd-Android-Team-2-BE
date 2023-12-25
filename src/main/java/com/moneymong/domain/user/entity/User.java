package com.moneymong.domain.user.entity;

import com.moneymong.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE users SET deleted = true where id=?")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "user_token",
            unique = true,
            nullable = false
    )
    private String userToken;

    @Column(nullable = false)
    private String nickname;

    @Column(
            name = "profile_image_url",
            nullable = false,
            length = 2500
    )
    private String profileImageUrl;

    @Column(nullable = false)
    private String provider;

    @Column(
            name = "oauth_id",
            nullable = false
    )
    private String oauthId;

    private LocalDate birthDay;

    @Builder
    private User(Long id, String userToken, String nickname, String profileImgUrl, String provider, String oauthId, LocalDate birthDay) {
        this.id = id;
        this.userToken = userToken;
        this.nickname = nickname;
        this.profileImageUrl = profileImgUrl;
        this.provider = provider;
        this.oauthId = oauthId;
        this.birthDay = birthDay;
    }
}
