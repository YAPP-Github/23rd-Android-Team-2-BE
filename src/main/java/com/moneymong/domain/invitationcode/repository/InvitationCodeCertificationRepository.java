package com.moneymong.domain.invitationcode.repository;

import com.moneymong.domain.invitationcode.entity.InvitationCodeCertification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationCodeCertificationRepository extends JpaRepository<InvitationCodeCertification, Long>, InvitationCodeCertificationRepositoryCustom {
}
