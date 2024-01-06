package com.moneymong.domain.university.repository;

import com.moneymong.domain.university.University;

import java.util.List;

public interface UniversityRepositoryCustom {
    List<University> findByKeyword(String keyword);
}
