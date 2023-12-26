package com.moneymong.domain.user.repository;

import com.moneymong.domain.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.moneymong.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<User> findByUserIdByProviderAndOauthId(String provider, String oauthId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(user)
                .where(eqProvider(provider), eqOauthId(oauthId))
                .fetchOne()
        );
    }

    public BooleanExpression eqProvider(String provider) {
        return user.provider.eq(provider);
    }

    public BooleanExpression eqOauthId(String oauthId) {
        return user.oauthId.eq(oauthId);
    }
}
