package com.moneymong.global.security.token.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRefreshTokenRequest {

    @NotBlank
    private String refreshToken;
}
