package com.moneymong.domain.university.repository;

import com.moneymong.domain.university.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Long>, UniversityRepositoryCustom{
}
