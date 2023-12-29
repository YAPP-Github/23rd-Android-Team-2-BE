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
public class UpdateUserUniversityRequest {
    @NotBlank(message = "대학교 이름은 필수 입력값입니다.")
    private String universityName;

    @Min(value = 2, message = "학년은 {value} 이상이어야 합니다.")
    @Max(value = 5, message = "학년은 {value} 이하여야 합니다. ")
    private int grade;
}
