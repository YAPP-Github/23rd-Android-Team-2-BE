package com.moneymong.domain.user.api.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserUniversityRequest {
    @NotBlank
    private String universityName;

    @Min(value = 1)
    @Max(value = 5)
    private int grade;
}
