package com.moneymong.domain.invitationcode.repository;

import com.moneymong.domain.invitationcode.entity.InvitationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationCodeRepository extends JpaRepository<InvitationCode, Long> {
    Optional<InvitationCode> findByAgencyId(Long agencyId);
}
