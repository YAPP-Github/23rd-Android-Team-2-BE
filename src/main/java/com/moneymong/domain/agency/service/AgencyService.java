package com.moneymong.domain.agency.service;

import com.moneymong.domain.agency.api.request.CreateAgencyRequest;
import com.moneymong.domain.agency.api.response.CreateAgencyResponse;
import com.moneymong.domain.agency.entity.Agency;
import com.moneymong.domain.agency.entity.AgencyUser;
import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
import com.moneymong.domain.agency.repository.AgencyRepository;
import com.moneymong.domain.agency.repository.AgencyUserRepository;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AgencyService {
    private static final int INITIAL_HEAD_COUNT = 1;
    private final AgencyUserRepository agencyUserRepository;
    private final AgencyRepository agencyRepository;

    public AgencyUser validateAgencyUser(
            final Long userId,
            final Long agencyId
    ) {
        return agencyUserRepository
                .findByUserIdAndAgencyId(userId, agencyId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.AGENCY_NOT_FOUND));
    }

    @Transactional
    public CreateAgencyResponse create(Long userId, CreateAgencyRequest request) {
        Agency agency = Agency.of(
                request.getName(),
                request.getAgencyType(),
                request.getThumbnailImage(),
                request.getDescription(),
                INITIAL_HEAD_COUNT
        );

        AgencyUser agencyUser = AgencyUser.of(agency, userId, AgencyUserRole.STAFF);

        agency.addAgencyUser(agencyUser);

        agencyRepository.save(agency);

        return new CreateAgencyResponse(agency.getId());
    }
}
