package com.moneymong.domain.agency.repository;

import com.moneymong.domain.agency.api.response.AgencyUserResponse;

import java.util.List;

public interface AgencyUserRepositoryCustom {
    List<AgencyUserResponse> findByAgencyId(Long agencyId);
}
