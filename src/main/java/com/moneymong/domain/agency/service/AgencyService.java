package com.moneymong.domain.agency.service;

import com.moneymong.domain.agency.api.request.CreateAgencyRequest;
import com.moneymong.domain.agency.api.response.AgencyResponse;
import com.moneymong.domain.agency.api.response.CreateAgencyResponse;
import com.moneymong.domain.agency.api.response.SearchAgencyResponse;
import com.moneymong.domain.agency.entity.Agency;
import com.moneymong.domain.agency.entity.AgencyUser;
import com.moneymong.domain.agency.entity.enums.AgencyType;
import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
import com.moneymong.domain.agency.repository.AgencyRepository;
import com.moneymong.domain.agency.repository.AgencyUserRepository;
import com.moneymong.domain.user.entity.UserUniversity;
import com.moneymong.domain.user.repository.UserRepository;
import com.moneymong.domain.user.repository.UserUniversityRepository;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import com.moneymong.global.security.token.dto.jwt.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgencyService {
    private static final int INITIAL_HEAD_COUNT = 1;
    private final AgencyUserRepository agencyUserRepository;
    private final AgencyRepository agencyRepository;
    private final UserUniversityRepository userUniversityRepository;

    public AgencyUser validateAgencyUser(
            final Long userId,
            final Long agencyId
    ) {
        return agencyUserRepository
                .findByUserIdAndAgencyId(userId, agencyId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.AGENCY_NOT_FOUND));
    }

    public SearchAgencyResponse getAgencyList(Long userId, Pageable pageable) {
        String universityName = getUniversityName(userId);

        Page<Agency> findByUniversityNameResult = agencyRepository.findByUniversityNameByPaging(universityName, pageable);

        long totalCount = findByUniversityNameResult.getTotalElements();

        List<AgencyResponse> responseList = findByUniversityNameResult.stream()
                .map(AgencyResponse::from)
                .toList();

        return new SearchAgencyResponse(responseList, totalCount);
    }

    @Transactional
    public CreateAgencyResponse create(Long userId, CreateAgencyRequest request) {
        String universityName = getUniversityName(userId);

        Agency agency = Agency.of(
                request.getName(),
                request.getAgencyType(),
                request.getDescription(),
                INITIAL_HEAD_COUNT,
                universityName
        );

        AgencyUser agencyUser = AgencyUser.of(agency, userId, AgencyUserRole.STAFF);

        agency.addAgencyUser(agencyUser);

        agencyRepository.save(agency);

        return new CreateAgencyResponse(agency.getId());
    }

    private String getUniversityName(Long userId) {
        UserUniversity university = userUniversityRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_UNIVERSITY_NOT_FOUND));

        return university.getUniversityName();
    }
}
