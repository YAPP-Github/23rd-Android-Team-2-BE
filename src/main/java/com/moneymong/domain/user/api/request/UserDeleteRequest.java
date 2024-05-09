package com.moneymong.domain.user.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserDeleteRequest {
    @NotBlank
    private String provider;

    @NotBlank
    private String token;
}
