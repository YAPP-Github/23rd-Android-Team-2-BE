package com.moneymong.domain.invitationcode.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CertifyInvitationCodeRequest {

    @NotBlank
    private String invitationCode;
}
