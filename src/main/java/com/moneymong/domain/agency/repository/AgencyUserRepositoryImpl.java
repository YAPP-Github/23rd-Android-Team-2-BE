package com.moneymong.domain.agency.repository;

import com.moneymong.domain.agency.api.response.AgencyUserResponse;
import com.moneymong.domain.agency.entity.Agency;
import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moneymong.domain.agency.entity.QAgency.agency;
import static com.moneymong.domain.agency.entity.QAgencyUser.agencyUser;
import static com.moneymong.domain.user.entity.QUser.*;

@Repository
@RequiredArgsConstructor
public class AgencyUserRepositoryImpl implements AgencyUserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AgencyUserResponse> findByAgencyId(Long agencyId) {
        return queryFactory.select(
                        Projections.constructor(AgencyUserResponse.class,
                                agencyUser.id,
                                agencyUser.userId,
                                user.nickname,
                                agencyUser.agencyUserRole
                        )
                ).from(agencyUser)
                .where(eqAgencyId(agencyId), notBlocked())
                .join(user)
                .on(agencyUser.userId.eq(user.id))
                .fetch();
    }

    @Override
    public List<Agency> findAgencyListByUserId(Long userId) {
        return queryFactory.select(agency)
                .from(agencyUser)
                .join(agencyUser.agency, agency)
                .where(agencyUser.userId.eq(userId))
                .fetch();
    }

    public BooleanExpression eqAgencyId(Long agencyId) {
        return agencyId != null ? agencyUser.agency.id.eq(agencyId) : null;
    }

    public BooleanExpression notBlocked() {
        return agencyUser.agencyUserRole.eq(AgencyUserRole.BLOCKED).not();
    }
}
