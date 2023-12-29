package com.moneymong.domain.user.entity;

import com.moneymong.global.domain.BaseEntity;
import com.moneymong.utils.Validator;
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

import static lombok.AccessLevel.PROTECTED;

@Table(name = "user_universities")
@Entity
@Getter
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE user_university SET deleted = true where id=?")
public class UserUniversity extends BaseEntity {

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
        Validator.checkText(universityName, "대학 이름은 필수 입력값입니다.");

        this.universityName = universityName;
        this.grade = grade;
    }

    public static UserUniversity of(Long userId, String universityName, int grade) {
        return UserUniversity.builder()
                .userId(userId)
                .universityName(universityName)
                .grade(grade)
                .build();
    }
}
