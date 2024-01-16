package com.moneymong.domain.invitationcode.repository;

import com.moneymong.domain.invitationcode.entity.InvitationCodeCertification;

import java.util.Optional;

public interface InvitationCodeCertificationRepositoryCustom {
    Optional<InvitationCodeCertification> findByUserIdAndAgencyId(Long userId, Long agencyId);
}
