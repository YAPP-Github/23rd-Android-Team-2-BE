package com.moneymong.domain.useruniversity;

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

@Table(name = "user_university")
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE user_university SET deleted = true where id=?")
public class UserUniversity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userToken;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private int grade;

    @Builder
    private UserUniversity(Long id, String userToken, String name, int grade) {
        this.id = id;
        this.userToken = userToken;
        this.name = name;
        this.grade = grade;
    }
}
