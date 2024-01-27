package com.moneymong.domain.invitationcode.service;

import com.moneymong.domain.agency.entity.AgencyUser;
import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
import com.moneymong.domain.agency.repository.AgencyUserRepository;
import com.moneymong.domain.agency.service.AgencyUserService;
import com.moneymong.domain.invitationcode.api.request.CertifyInvitationCodeRequest;
import com.moneymong.domain.invitationcode.api.response.CertifyInvitationCodeResponse;
import com.moneymong.domain.invitationcode.api.response.InvitationCodeResponse;
import com.moneymong.domain.invitationcode.entity.CertificationStatus;
import com.moneymong.domain.invitationcode.entity.InvitationCode;
import com.moneymong.domain.invitationcode.entity.InvitationCodeCertification;
import com.moneymong.domain.invitationcode.repository.InvitationCodeCertificationRepository;
import com.moneymong.domain.invitationcode.repository.InvitationCodeRepository;
import com.moneymong.global.exception.custom.InvalidAccessException;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import com.moneymong.utils.RandomCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InvitationCodeService {
    private final InvitationCodeRepository invitationCodeRepository;
    private final AgencyUserRepository agencyUserRepository;
    private final AgencyUserService agencyUserService;
    private final InvitationCodeCertificationRepository invitationCodeCertificationRepository;

    @Transactional
    public InvitationCodeResponse updateCode(Long userId, Long agencyId) {
        AgencyUser agencyUser = getAgencyUser(userId, agencyId);

        AgencyUserRole userRole = agencyUser.getAgencyUserRole();

        validateStaffUserRole(userRole);

        InvitationCode invitationCode = getInvitationCode(agencyId);

        invitationCode.update(RandomCodeGenerator.generateCode());

        return InvitationCodeResponse.from(invitationCode.getCode());
    }

    @Transactional(readOnly = true)
    public InvitationCodeResponse getCode(Long userId, Long agencyId) {
        AgencyUser agencyUser = getAgencyUser(userId, agencyId);

        AgencyUserRole userRole = agencyUser.getAgencyUserRole();

        validateStaffUserRole(userRole);

        InvitationCode invitationCode = getInvitationCode(agencyId);

        return InvitationCodeResponse.from(invitationCode.getCode());
    }

    @Transactional
    public CertifyInvitationCodeResponse certify(CertifyInvitationCodeRequest request, Long userId, Long agencyId) {
        InvitationCode invitationCode = getInvitationCode(agencyId);

        boolean certified = invitationCode.isSameCode(request.getInvitationCode());

        if (certified) {
            InvitationCodeCertification certification = getCertification(userId, agencyId);
            invitationCodeCertificationRepository.save(certification);

            agencyUserService.join(agencyId, userId);
        }

        return CertifyInvitationCodeResponse.from(certified);
    }

    private InvitationCodeCertification getCertification(Long userId, Long agencyId) {
        return invitationCodeCertificationRepository.findByUserIdAndAgencyId(userId, agencyId)
                .orElseGet(() -> InvitationCodeCertification.of(userId, agencyId, CertificationStatus.DONE));
    }

    private AgencyUser getAgencyUser(Long userId, Long agencyId) {
        return agencyUserRepository.findByUserIdAndAgencyId(userId, agencyId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.AGENCY_USER_NOT_FOUND));
    }

    private InvitationCode getInvitationCode(Long agencyId) {
        return invitationCodeRepository.findByAgencyId(agencyId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.INVITATION_CODE_NOT_FOUND));
    }

    private void validateStaffUserRole(AgencyUserRole userRole) {
        if (!AgencyUserRole.isStaffUser(userRole)) {
            throw new InvalidAccessException(ErrorCode.INVALID_AGENCY_USER_ACCESS);
        }
    }
}
