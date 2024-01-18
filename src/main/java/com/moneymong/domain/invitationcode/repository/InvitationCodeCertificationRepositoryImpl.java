package com.moneymong.domain.invitationcode.repository;

import com.moneymong.domain.invitationcode.entity.InvitationCodeCertification;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.moneymong.domain.invitationcode.entity.CertificationStatus.DONE;
import static com.moneymong.domain.invitationcode.entity.QInvitationCodeCertification.invitationCodeCertification;

@Repository
@RequiredArgsConstructor
public class InvitationCodeCertificationRepositoryImpl implements InvitationCodeCertificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<InvitationCodeCertification> findByUserIdAndAgencyId(Long userId, Long agencyId) {
        return Optional.ofNullable(
        queryFactory.selectFrom(invitationCodeCertification)
                .where(
                        invitationCodeCertification.userId.eq(userId),
                        invitationCodeCertification.agencyId.eq(agencyId),
                        invitationCodeCertification.status.eq(DONE)
                )
                .fetchOne()
        );
    }
}
