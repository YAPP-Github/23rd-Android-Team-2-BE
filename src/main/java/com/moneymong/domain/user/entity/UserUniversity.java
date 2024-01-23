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

import static com.moneymong.utils.Validator.checkText;
import static lombok.AccessLevel.PROTECTED;

@Table(name = "user_universities")
@Entity
@Getter
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
public class UserUniversity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "user_id",
            unique = true,
            nullable = false
    )
    private Long userId;

    @Column(
            name = "university_name",
            length = 100,
            nullable = false
    )
    private String universityName;

    @Column(nullable = false)
    private int grade;

    public void update(String universityName, int grade) {
        checkText(universityName, "대학 이름은 필수 입력값입니다.");

        this.universityName = universityName;
        this.grade = grade;
    }

    public static UserUniversity of(Long userId, String universityName, int grade) {
        checkText(universityName, "대학 이름은 필수 입력값입니다.");

        return UserUniversity.builder()
                .userId(userId)
                .universityName(universityName)
                .grade(grade)
                .build();
    }
}
