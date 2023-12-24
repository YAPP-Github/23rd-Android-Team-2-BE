package com.moneymong.domain.agency.service;

import com.moneymong.domain.agency.entity.AgencyUser;
import com.moneymong.domain.agency.repository.AgencyUserRepository;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgencyService {
    private final AgencyUserRepository agencyUserRepository;

    public AgencyUser validateAgencyUser(
            final Long userId,
            final Long agencyId
    ) {
        return agencyUserRepository
                .findByUserIdAndAgencyId(userId, agencyId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.AGENCY_NOT_FOUND));
    }
}
