package com.moneymong.domain.university.api;

import com.moneymong.domain.university.api.response.SearchUniversityResponse;
import com.moneymong.domain.university.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/universities")
@RequiredArgsConstructor
@RestController
public class UniversityController {

    private final UniversityService universityService;

    @GetMapping
    public SearchUniversityResponse findByKeyword(@RequestParam("keyword") String keyword) {
        return universityService.findByKeyword(keyword);
    }
}
