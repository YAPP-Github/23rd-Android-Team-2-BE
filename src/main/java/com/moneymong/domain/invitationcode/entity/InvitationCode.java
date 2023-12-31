package com.moneymong.domain.invitationcode.entity;

import com.moneymong.domain.agency.entity.Agency;
import com.moneymong.domain.ledger.entity.Ledger;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

import static lombok.AccessLevel.PROTECTED;

@Table(name = "invitation_codes")
@Entity
@Getter
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
public class InvitationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long agencyId;

    @Column(nullable = false)
    private String code;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    public static InvitationCode of(
            Long agencyId,
            String code
    ) {
        return InvitationCode.builder()
                .agencyId(agencyId)
                .code(code)
                .build();
    }

    public void update(String code) {
        this.code = code;
    }
}
