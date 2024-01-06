package com.moneymong.domain.university.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SearchUniversityResponse {
    private List<UniversityResponse> universities;
}
