package com.moneymong.domain.agency.repository;

import com.moneymong.domain.agency.entity.AgencyUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyUserRepository extends JpaRepository<AgencyUser, Long>, AgencyUserRepositoryCustom {
    Optional<AgencyUser> findByUserIdAndAgencyId(final Long userId, final Long agencyId);
}
