package com.moneymong.domain.agency.repository;

import com.moneymong.domain.agency.entity.Agency;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moneymong.domain.agency.entity.QAgency.agency;

@Repository
@RequiredArgsConstructor
public class AgencyRepositoryImpl implements AgencyRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Agency> findByUniversityNameByPaging(String universityName, Pageable pageable) {
        JPAQuery<Agency> query = queryFactory.selectFrom(agency)
                .where(eqUniversityName(universityName))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        List<Agency> result = query.fetch();
        JPAQuery<Agency> countQuery = getCountQuery(universityName);

        return PageableExecutionUtils.getPage(result, pageable, () -> countQuery.fetch().size());
    }

    private JPAQuery<Agency> getCountQuery(String universityName) {
        return queryFactory.selectFrom(agency)
                .where(eqUniversityName(universityName));
    }

    private BooleanExpression eqUniversityName(String universityName) {
        return universityName != null ? agency.universityName.eq(universityName) : null;
    }
}
