package com.moneymong.domain.ledger.service.reader;

import com.moneymong.domain.agency.entity.AgencyUser;
import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
import com.moneymong.domain.agency.repository.AgencyUserRepository;
import com.moneymong.domain.ledger.api.response.ledger.LedgerInfoView;
import com.moneymong.domain.ledger.api.response.ledger.LedgerInfoViewDetail;
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
import java.util.stream.IntStream;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.moneymong.domain.agency.entity.enums.AgencyUserRole.isBlockedUser;
import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LedgerReader {
    private final UserRepository userRepository;
    private final AgencyUserRepository agencyUserRepository;
    private final LedgerRepository ledgerRepository;
    private final LedgerDetailRepository ledgerDetailRepository;

    public LedgerInfoView searchLedgersByPeriod(
            long userId,
            long ledgerId,
            int startYear,
            int startMonth,
            int endYear,
            int endMonth,
            int page,
            int limit
    ) {
        Ledger ledger = getLedger(ledgerId);
        AgencyUser agencyUser = getAgencyUser(userId, ledger);

        ensureAgencyUserNotBlocked(agencyUser.getAgencyUserRole());

        ZonedDateTime from = ZonedDateTime.of(startYear, startMonth, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime to = ZonedDateTime.of(endYear, endMonth, 1, 0, 0, 0, 0, ZoneId.systemDefault());

        List<LedgerDetail> ledgerDetails = ledgerDetailRepository.searchByPeriod(
                ledger,
                from,
                to,
                PageRequest.of(page, limit)
        );

        return LedgerInfoView.from(ledger, convertToLedgerInfoViewDetail(ledgerDetails));
    }

    public LedgerInfoView searchLedgersByPeriodAndFundType(
            long userId,
            long ledgerId,
            int startYear,
            int startMonth,
            int endYear,
            int endMonth,
            int page,
            int limit,
            FundType fundType
    ) {
        Ledger ledger = getLedger(ledgerId);
        AgencyUser agencyUser = getAgencyUser(userId, ledger);

        ensureAgencyUserNotBlocked(agencyUser.getAgencyUserRole());

        ZonedDateTime from = ZonedDateTime.of(startYear, startMonth, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime to = ZonedDateTime.of(endYear, endMonth, 1, 0, 0, 0, 0, ZoneId.systemDefault());

        List<LedgerDetail> ledgerDetails = ledgerDetailRepository.searchByPeriodAndFundType(
                ledger,
                from,
                to,
                fundType,
                PageRequest.of(page, limit)
        );

        return LedgerInfoView.from(ledger, convertToLedgerInfoViewDetail(ledgerDetails));
    }

    public LedgerInfoView search(
            final Long userId,
            final Long ledgerId,
            final Integer year,
            final Integer month,
            final Integer page,
            final Integer limit
    ) {
        // === 유저 ===
        User user = getUser(userId);

        // === 장부 ===
        Ledger ledger = getLedger(ledgerId);

        // === 소속 ===
        AgencyUser agencyUser = getAgencyUser(userId, ledger);

        ensureAgencyUserNotBlocked(agencyUser.getAgencyUserRole());

        ZonedDateTime from = ZonedDateTime.of(year, month, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime to = from.plusMonths(1);
        List<LedgerDetail> ledgerDetailPage = ledgerDetailRepository.search(
                ledger,
                from,
                to,
                PageRequest.of(page, limit)
        );

        return LedgerInfoView.from(ledger, convertToLedgerInfoViewDetail(ledgerDetailPage));
    }

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
        User user = getUser(userId);

        // === 장부 ===
        Ledger ledger = getLedger(ledgerId);

        // === 소속 ===
        AgencyUser agencyUser = getAgencyUser(userId, ledger);

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

        return LedgerInfoView.from(ledger, convertToLedgerInfoViewDetail(ledgerDetailPage));
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
        User user = getUser(userId);

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

        return LedgerInfoView.from(ledger, convertToLedgerInfoViewDetail(ledgerDetailPage));
    }

    public boolean exists(Long agencyId) {
        Ledger ledger = ledgerRepository.findByAgencyId(agencyId).orElseThrow(
                () -> new NotFoundException(ErrorCode.LEDGER_NOT_FOUND)
        );

        return ledgerDetailRepository.existsByLedger(ledger);
    }

    private User getUser(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private Ledger getLedger(Long ledgerId) {
        return ledgerRepository.findById(ledgerId).orElseThrow(
                () -> new NotFoundException(ErrorCode.LEDGER_NOT_FOUND)
        );
    }

    private AgencyUser getAgencyUser(Long userId, Ledger ledger) {
        return agencyUserRepository
                .findByUserIdAndAgencyId(userId, ledger.getAgency().getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.AGENCY_NOT_FOUND));
    }

    private void ensureAgencyUserNotBlocked(AgencyUserRole role) {
        if (isBlockedUser(role)) {
            throw new BadRequestException(ErrorCode.BLOCKED_AGENCY_USER);
        }
    }

    private List<LedgerInfoViewDetail> convertToLedgerInfoViewDetail(List<LedgerDetail> ledgerDetails) {
        int count = ledgerDetails.size();

        return IntStream.iterate(count, i -> i > 0, i -> i - 1)
                .mapToObj(index -> createLedgerInfoViewDetail(ledgerDetails.get(count - index), index))
                .collect(toList());
    }

    private LedgerInfoViewDetail createLedgerInfoViewDetail(LedgerDetail ledgerDetail, int order) {
        return LedgerInfoViewDetail.builder()
                .id(ledgerDetail.getId())
                .storeInfo(ledgerDetail.getStoreInfo())
                .fundType(ledgerDetail.getFundType())
                .amount(ledgerDetail.getAmount())
                .balance(ledgerDetail.getBalance())
                .order(order)
                .paymentDate(ledgerDetail.getPaymentDate())
                .build();
    }
}
