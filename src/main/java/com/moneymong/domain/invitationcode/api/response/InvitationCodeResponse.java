package com.moneymong.domain.invitationcode.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InvitationCodeResponse {
    private String code;

    public static InvitationCodeResponse from(String code) {
        return InvitationCodeResponse.builder()
                .code(code)
                .build();
    }
}
