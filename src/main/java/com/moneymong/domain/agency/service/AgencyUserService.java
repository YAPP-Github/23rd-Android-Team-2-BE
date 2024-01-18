package com.moneymong.domain.agency.service;

import com.moneymong.domain.agency.api.request.BlockAgencyUserRequest;
import com.moneymong.domain.agency.api.request.UpdateAgencyUserRoleRequest;
import com.moneymong.domain.agency.entity.AgencyUser;
import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
import com.moneymong.domain.agency.repository.AgencyUserRepository;
import com.moneymong.domain.invitationcode.entity.InvitationCodeCertification;
import com.moneymong.domain.invitationcode.repository.InvitationCodeCertificationRepository;
import com.moneymong.global.exception.custom.InvalidAccessException;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.moneymong.domain.agency.entity.enums.AgencyUserRole.BLOCKED;

@Service
@RequiredArgsConstructor
public class AgencyUserService {

    private final AgencyUserRepository agencyUserRepository;
    private final InvitationCodeCertificationRepository invitationCodeCertificationRepository;

    @Transactional
    public void changeUserRole(Long userId, Long agencyId, UpdateAgencyUserRoleRequest request) {
        AgencyUser staffUser = getAgencyUser(userId, agencyId);

        validateStaffUserRole(staffUser);

        AgencyUser targetUser = getAgencyUser(request.getUserId(), agencyId);

        targetUser.updateAgencyUserRole(request.getRole());
    }

    @Transactional
    public void blockUser(Long userId, Long agencyId, BlockAgencyUserRequest request) {
        AgencyUser staffUser = getAgencyUser(userId, agencyId);

        validateStaffUserRole(staffUser);

        AgencyUser targetUser = getAgencyUser(request.getUserId(), agencyId);

        targetUser.updateAgencyUserRole(BLOCKED);

        InvitationCodeCertification certification = getCertification(agencyId, request);

        certification.revoke();
    }

    private AgencyUser getAgencyUser(Long request, Long agencyId) {
        return agencyUserRepository.findByUserIdAndAgencyId(request, agencyId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.AGENCY_USER_NOT_FOUND));
    }

    private InvitationCodeCertification getCertification(Long agencyId, BlockAgencyUserRequest request) {
        return invitationCodeCertificationRepository.findByUserIdAndAgencyId(request.getUserId(), agencyId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.INVITATION_CODE_NOT_CERTIFIED_USER));
    }

    private void validateStaffUserRole(AgencyUser staffUser) {
        if (!AgencyUserRole.isStaffUser(staffUser.getAgencyUserRole())) {
            throw new InvalidAccessException(ErrorCode.INVALID_AGENCY_USER_ACCESS);
        }
    }
}
