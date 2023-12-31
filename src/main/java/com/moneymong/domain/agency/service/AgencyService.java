package com.moneymong.domain.agency.service;

import com.moneymong.domain.agency.api.request.CreateAgencyRequest;
import com.moneymong.domain.agency.api.response.AgencyResponse;
import com.moneymong.domain.agency.api.response.CreateAgencyResponse;
import com.moneymong.domain.agency.api.response.SearchAgencyResponse;
import com.moneymong.domain.agency.entity.Agency;
import com.moneymong.domain.agency.entity.AgencyUser;
import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
import com.moneymong.domain.agency.repository.AgencyRepository;
import com.moneymong.domain.agency.repository.AgencyUserRepository;
import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.repository.LedgerRepository;
import com.moneymong.domain.user.entity.UserUniversity;
import com.moneymong.domain.user.repository.UserUniversityRepository;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgencyService {

    private static final int INITIAL_HEAD_COUNT = 1;
    private final AgencyUserRepository agencyUserRepository;
    private final AgencyRepository agencyRepository;
    private final UserUniversityRepository userUniversityRepository;
    private final LedgerRepository ledgerRepository;

    public SearchAgencyResponse getAgencyList(Long userId, Pageable pageable) {
        String universityName = getUniversityName(userId);

        Page<Agency> findByUniversityNameResult = agencyRepository.findByUniversityNameByPaging(universityName,
                pageable);

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

        // === 장부 ===
        Ledger ledger = Ledger.of(agency, 0);
        ledgerRepository.save(ledger);

        return new CreateAgencyResponse(agency.getId());
    }

    private String getUniversityName(Long userId) {
        UserUniversity university = userUniversityRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_UNIVERSITY_NOT_FOUND));

        return university.getUniversityName();
    }

}
