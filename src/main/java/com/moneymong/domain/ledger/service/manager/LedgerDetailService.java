package com.moneymong.domain.ledger.service.manager;

import com.moneymong.domain.agency.entity.AgencyUser;
import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
import com.moneymong.domain.agency.repository.AgencyUserRepository;
import com.moneymong.domain.ledger.api.request.UpdateLedgerRequest;
import com.moneymong.domain.ledger.api.response.LedgerDetailInfoView;
import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import com.moneymong.domain.ledger.entity.LedgerReceipt;
import com.moneymong.domain.ledger.entity.enums.FundType;
import com.moneymong.domain.ledger.repository.LedgerDetailRepository;
import com.moneymong.domain.ledger.repository.LedgerDocumentRepository;
import com.moneymong.domain.ledger.repository.LedgerReceiptRepository;
import com.moneymong.domain.ledger.repository.LedgerRepository;
import com.moneymong.domain.ledger.service.mapper.LedgerAssembler;
import com.moneymong.domain.ledger.service.reader.LedgerDocumentReader;
import com.moneymong.domain.ledger.service.reader.LedgerReceiptReader;
import com.moneymong.domain.user.entity.User;
import com.moneymong.domain.user.repository.UserRepository;
import com.moneymong.global.exception.custom.InvalidAccessException;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

import com.moneymong.utils.AmountCalculatorByFundType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.moneymong.domain.ledger.entity.enums.FundType.INCOME;

@Service
@Slf4j
@RequiredArgsConstructor
public class LedgerDetailService {
    private final LedgerRepository ledgerRepository;
    private final LedgerAssembler ledgerAssembler;
    private final LedgerReceiptReader ledgerReceiptReader;
    private final LedgerDocumentReader ledgerDocumentReader;
    private final UserRepository userRepository;
    private final AgencyUserRepository agencyUserRepository;
    private final LedgerDetailRepository ledgerDetailRepository;
    private final LedgerReceiptRepository ledgerReceiptRepository;
    private final LedgerDocumentRepository ledgerDocumentRepository;

    @Transactional
    public LedgerDetail createLedgerDetail(
            final Ledger ledger,
            final User user,
            final String storeInfo,
            final FundType fundType,
            final Integer amount,
            final Integer balance,
            final String description,
            final ZonedDateTime paymentDate
    ) {

        LedgerDetail ledgerDetail = ledgerAssembler.toLedgerDetailEntity(
                ledger,
                user,
                storeInfo,
                fundType,
                amount,
                balance,
                description,
                paymentDate
        );

        int newAmount = AmountCalculatorByFundType.calculate(fundType, amount);

        ledger.updateTotalBalance(newAmount);

        ledgerDetailRepository.save(ledgerDetail);

        updateBalance(ledger);

        return ledgerDetail;
    }

    @Transactional
    public LedgerDetailInfoView updateLedgerDetail(
            User user,
            LedgerDetail ledgerDetail,
            UpdateLedgerRequest updateLedgerRequest
    ) {
        long ledgerDetailId = ledgerDetail.getId();

        Ledger ledger = ledgerDetail.getLedger();

        int newAmount = AmountCalculatorByFundType.calculate(
                ledgerDetail.getFundType(),
                ledgerDetail.getAmount() - updateLedgerRequest.getAmount()
        );

        ledger.updateTotalBalance(-newAmount);

        ledgerDetail.updateLedgerDetailInfo(
                updateLedgerRequest.getStoreInfo(),
                updateLedgerRequest.getAmount(),
                updateLedgerRequest.getDescription(),
                updateLedgerRequest.getPaymentDate()
        );

        AgencyUser agencyUser = getAgencyUser(user, ledgerDetail);

        validateStaffUserRole(agencyUser.getAgencyUserRole());

        updateBalance(ledger);

        // 2. 장부 상세 내역 조회
        List<LedgerReceipt> ledgerReceipts = ledgerReceiptReader.getLedgerReceipts(ledgerDetailId);
        List<LedgerDocument> ledgerDocuments = ledgerDocumentReader.getLedgerDocuments(ledgerDetailId);

        return LedgerDetailInfoView.of(
                ledgerDetail,
                ledgerReceipts,
                ledgerDocuments,
                user
        );
    }

    @Transactional
    public void removeLedgerDetail(
            final Long userId,
            final Long detailId
    ) {
        // === 유저 ===
        User user = getUser(userId);

        LedgerDetail ledgerDetail = getLedgerDetail(detailId);

        Ledger ledger = ledgerDetail.getLedger();

        // === 소속 ===
        AgencyUser agencyUser = getAgencyUser(user, ledgerDetail);

        // === 권한 ===
        validateStaffUserRole(agencyUser.getAgencyUserRole());

        ledgerReceiptRepository.deleteByLedgerDetail(ledgerDetail);
        ledgerDocumentRepository.deleteByLedgerDetail(ledgerDetail);
        ledgerDetailRepository.delete(ledgerDetail);

        int newAmount = AmountCalculatorByFundType.calculate(ledgerDetail.getFundType(), ledgerDetail.getAmount());

        ledger.updateTotalBalance(-newAmount);

        updateBalance(ledger);
    }

    private void validateStaffUserRole(AgencyUserRole userRole) {
        if (!AgencyUserRole.isStaffUser(userRole)) {
            throw new InvalidAccessException(ErrorCode.INVALID_AGENCY_USER_ACCESS);
        }
    }

    private AgencyUser getAgencyUser(User user, LedgerDetail ledgerDetail) {
        return agencyUserRepository
                .findByUserIdAndAgencyId(user.getId(), ledgerDetail.getLedger().getAgency().getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.AGENCY_NOT_FOUND));
    }

    private LedgerDetail getLedgerDetail(Long detailId) {
        return ledgerDetailRepository
                .findById(detailId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_DETAIL_NOT_FOUND));
    }

    private User getUser(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private void updateBalance(Ledger ledger) {
        List<LedgerDetail> ledgerDetails = ledgerDetailRepository.findAllByLedger(ledger);

        ledgerDetails.sort(Comparator.comparing(LedgerDetail::getPaymentDate));

        int previousBalance = 0;

        for (int i = 0; i < ledgerDetails.size(); i++) {
            LedgerDetail detail = ledgerDetails.get(i);

            if (detail.getFundType() == INCOME) {
                previousBalance += detail.getAmount();
            } else {
                previousBalance -= detail.getAmount();
            }

            detail.updateBalance(previousBalance);
        }
    }
}
