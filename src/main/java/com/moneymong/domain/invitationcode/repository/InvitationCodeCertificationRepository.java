package com.moneymong.domain.invitationcode.repository;

import com.moneymong.domain.invitationcode.entity.InvitationCodeCertification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationCodeCertificationRepository extends JpaRepository<InvitationCodeCertification, Long> {
    Optional<InvitationCodeCertification> findByUserIdAndAgencyId(Long userId, Long agencyId);
}
