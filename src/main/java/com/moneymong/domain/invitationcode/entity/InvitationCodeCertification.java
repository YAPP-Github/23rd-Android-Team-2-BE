package com.moneymong.domain.invitationcode.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

import static lombok.AccessLevel.PROTECTED;

@Table(
        name = "invitation_code_certification",
        indexes = @Index(name = "idx_userId_agencyId", columnList = "user_id, agency_id")
)
@Entity
@Getter
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
public class InvitationCodeCertification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long agencyId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CertificationStatus status;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
    
    public static InvitationCodeCertification of(Long userId, Long agencyId, CertificationStatus status) {
        return InvitationCodeCertification.builder()
                .userId(userId)
                .agencyId(agencyId)
                .status(status)
                .build();
    }

    public void revoke() {
        this.status = CertificationStatus.REVOKE;
    }
}
