package com.moneymong.domain.university.repository;

import com.moneymong.domain.university.University;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moneymong.domain.university.QUniversity.university;

@Repository
@RequiredArgsConstructor
public class UniversityRepositoryImpl implements UniversityRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<University> findByKeyword(String keyword) {
        return queryFactory.selectFrom(university)
                .where(university.schoolName.contains(keyword))
                .fetch();
    }
}
