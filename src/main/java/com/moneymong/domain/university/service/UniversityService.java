package com.moneymong.domain.university.service;


import com.moneymong.domain.university.University;
import com.moneymong.domain.university.api.response.SearchUniversityResponse;
import com.moneymong.domain.university.api.response.UniversityResponse;
import com.moneymong.domain.university.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityService {
    private final UniversityRepository universityRepository;

    @Transactional(readOnly = true)
    public SearchUniversityResponse findByKeyword(String keyword) {
        List<University> universities = universityRepository.findByKeyword(keyword);

        List<UniversityResponse> universityResponses = universities.stream()
                .map(university -> new UniversityResponse(university.getId(), university.getSchoolName()))
                .toList();

        return new SearchUniversityResponse(universityResponses);
    }
}
