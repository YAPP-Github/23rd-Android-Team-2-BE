package com.moneymong.domain.university.api.response;

import com.moneymong.domain.university.University;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UniversityResponse {
    private Long id;
    private String schoolName;

    public static UniversityResponse from(University university) {
        return UniversityResponse.builder()
                .id(university.getId())
                .schoolName(university.getSchoolName())
                .build();
    }
}
