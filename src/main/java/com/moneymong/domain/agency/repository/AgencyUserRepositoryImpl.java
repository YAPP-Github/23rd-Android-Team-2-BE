package com.moneymong.domain.agency.repository;

import com.moneymong.domain.agency.api.response.AgencyUserResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                .join(user)
                .on(agencyUser.userId.eq(user.id))
                .fetch();
    }
}
