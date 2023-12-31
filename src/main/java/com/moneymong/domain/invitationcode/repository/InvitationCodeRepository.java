package com.moneymong.domain.invitationcode.repository;

import com.moneymong.domain.invitationcode.entity.InvitationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationCodeRepository extends JpaRepository<InvitationCode, Long> {
}
