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

import static lombok.AccessLevel.PROTECTED;

@Table(name = "user_universities")
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE user_university SET deleted = true where id=?")
public class UserUniversity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "user_token",
            unique = true,
            nullable = false
    )
    private String userToken;

    @Column(
            name = "university_name",
            length = 100,
            nullable = false
    )
    private String universityName;

    @Column(nullable = false)
    private int grade;

    @Builder
    private UserUniversity(Long id, String userToken, String universityName, int grade) {
        this.id = id;
        this.userToken = userToken;
        this.universityName = universityName;
        this.grade = grade;
    }
}
