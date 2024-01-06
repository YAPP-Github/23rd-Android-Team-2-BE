package com.moneymong.domain.agency.repository;

import com.moneymong.domain.agency.entity.Agency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgencyRepositoryCustom {
    Page<Agency> findByUniversityNameByPaging(String universityName, Pageable pageable);
}
