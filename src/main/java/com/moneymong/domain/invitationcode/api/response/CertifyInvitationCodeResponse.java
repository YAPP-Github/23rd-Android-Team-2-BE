package com.moneymong.domain.invitationcode.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertifyInvitationCodeResponse {
    private boolean certified;

    public static CertifyInvitationCodeResponse from(boolean certified) {
        return CertifyInvitationCodeResponse.builder()
                .certified(certified)
                .build();
    }
}
