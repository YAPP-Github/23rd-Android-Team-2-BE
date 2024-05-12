package com.moneymong.domain.user.entity;

import com.moneymong.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE users SET deleted = true where id=?")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String provider;

    @Column(
            name = "oauth_id",
            nullable = false
    )
    private String oauthId;

    private LocalDate birthDay;

    public static User of(String email, String nickname, String provider, String oauthId) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .provider(provider)
                .oauthId(oauthId)
                .build();
    }
}
