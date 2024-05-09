package com.moneymong.domain.user.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank
    private String provider;

    private String accessToken;

    private String name;

    private String code;
}
