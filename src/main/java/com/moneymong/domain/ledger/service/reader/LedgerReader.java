package com.moneymong.domain.ledger.service.reader;

import com.moneymong.domain.agency.entity.AgencyUser;
import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
import com.moneymong.domain.agency.repository.AgencyUserRepository;
import com.moneymong.domain.ledger.api.response.ledger.LedgerInfoView;
import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.enums.FundType;
import com.moneymong.domain.ledger.repository.LedgerDetailRepository;
import com.moneymong.domain.ledger.repository.LedgerRepository;
import com.moneymong.domain.user.entity.User;
import com.moneymong.domain.user.repository.UserRepository;
import com.moneymong.global.exception.custom.BadRequestException;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.moneymong.domain.agency.entity.enums.AgencyUserRole.isBlockedUser;

@RequiredArgsConstructor
@Service
public class LedgerReader {
    private final UserRepository userRepository;
    private final AgencyUserRepository agencyUserRepository;
    private final LedgerRepository ledgerRepository;
    private final LedgerDetailRepository ledgerDetailRepository;

    @Transactional(readOnly = true)
    public LedgerInfoView search(
            final Long userId,
            final Long ledgerId,
            final Integer year,
            final Integer month,
            final Integer page,
            final Integer limit
    ) {
        // === 유저 ===
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // === 장부 ===
        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(
                () -> new NotFoundException(ErrorCode.LEDGER_NOT_FOUND)
        );

        // === 소속 ===
        AgencyUser agencyUser = agencyUserRepository
                .findByUserIdAndAgencyId(userId, ledger.getAgency().getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.AGENCY_NOT_FOUND));

        ensureAgencyUserNotBlocked(agencyUser.getAgencyUserRole());

        ZonedDateTime from = ZonedDateTime.of(year, month, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime to = from.plusMonths(1);
        List<LedgerDetail> ledgerDetailPage = ledgerDetailRepository.search(
                ledger,
                from,
                to,
                PageRequest.of(page, limit)
        );

        return LedgerInfoView.from(ledger, ledgerDetailPage);
    }

    @Transactional(readOnly = true)
    public LedgerInfoView searchByFilter(
            final Long userId,
            final Long ledgerId,
            final Integer year,
            final Integer month,
            final Integer page,
            final Integer limit,
            final FundType fundType
    ) {
        // === 유저 ===
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // === 장부 ===
        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(
                () -> new NotFoundException(ErrorCode.LEDGER_NOT_FOUND)
        );

        // === 소속 ===
        AgencyUser agencyUser = agencyUserRepository
                .findByUserIdAndAgencyId(userId, ledger.getAgency().getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.AGENCY_NOT_FOUND));

        ensureAgencyUserNotBlocked(agencyUser.getAgencyUserRole());

        ZonedDateTime from = ZonedDateTime.of(year, month, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime to = from.plusMonths(1);
        List<LedgerDetail> ledgerDetailPage = ledgerDetailRepository.searchByFundType(
                ledger,
                from,
                to,
                fundType,
                PageRequest.of(page, limit)
        );

        return LedgerInfoView.from(ledger, ledgerDetailPage);
    }


    public LedgerInfoView searchByAgency(
            final Long userId,
            final Long agencyId,
            final Integer year,
            final Integer month,
            final Integer page,
            final Integer limit
    ) {
        // === 유저 ===
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // === 장부 ===
        Ledger ledger = ledgerRepository.findByAgencyId(agencyId).orElseThrow(
                () -> new NotFoundException(ErrorCode.LEDGER_NOT_FOUND)
        );

        // === 소속 ===
        AgencyUser agencyUser = agencyUserRepository
                .findByUserIdAndAgencyId(user.getId(), agencyId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.AGENCY_NOT_FOUND));

        ensureAgencyUserNotBlocked(agencyUser.getAgencyUserRole());

        ZonedDateTime from = ZonedDateTime.of(year, month, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime to = from.plusMonths(1);
        List<LedgerDetail> ledgerDetailPage = ledgerDetailRepository.search(
                ledger,
                from,
                to,
                PageRequest.of(page, limit)
        );

        return LedgerInfoView.from(ledger, ledgerDetailPage);
    }

    public boolean exists(Long agencyId) {
        Ledger ledger = ledgerRepository.findByAgencyId(agencyId).orElseThrow(
                () -> new NotFoundException(ErrorCode.LEDGER_NOT_FOUND)
        );

        return ledgerDetailRepository.existsByLedger(ledger);
    }

    private void ensureAgencyUserNotBlocked(AgencyUserRole role) {
        if (isBlockedUser(role)) {
            throw new BadRequestException(ErrorCode.BLOCKED_AGENCY_USER);
        }
    }
}
