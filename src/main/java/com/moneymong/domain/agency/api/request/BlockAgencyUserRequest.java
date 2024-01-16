package com.moneymong.domain.agency.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BlockAgencyUserRequest {

    @NotBlank
    private Long userId;
}
