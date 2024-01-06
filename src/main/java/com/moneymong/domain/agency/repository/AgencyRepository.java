package com.moneymong.domain.agency.repository;

import com.moneymong.domain.agency.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency, Long>, AgencyRepositoryCustom {
}
