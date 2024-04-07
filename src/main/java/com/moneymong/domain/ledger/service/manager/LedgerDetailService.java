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
import com.moneymong.domain.ledger.service.mapper.LedgerAssembler;
import com.moneymong.domain.ledger.service.reader.LedgerDocumentReader;
import com.moneymong.domain.ledger.service.reader.LedgerReceiptReader;
import com.moneymong.domain.user.entity.User;
import com.moneymong.domain.user.repository.UserRepository;
import com.moneymong.global.exception.custom.InvalidAccessException;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import com.moneymong.utils.AmountCalculatorByFundType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerDetailService {
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

        /**
         * 가장 오래된 장부 내역인 경우 잔고를 amount 값으로 설정한다.
         * 이전 내역이 있는 경우, 가장 가까운 시일에 생성된 장부 내역을 기준으로 잔고를 저장한다.
         */
        Optional<LedgerDetail> mostRecentLedgerDetail = ledgerDetailRepository.findMostRecentLedgerDetail(ledger, paymentDate);

        if (mostRecentLedgerDetail.isPresent()) {
            LedgerDetail recentDetail = mostRecentLedgerDetail.get();

            int newAmount = recentDetail.getBalance() + AmountCalculatorByFundType.calculate(fundType, amount);
            ledgerDetail.updateBalance(newAmount);
        }else {
            ledgerDetail.updateBalance(AmountCalculatorByFundType.calculate(fundType, amount));
        }

        ledgerDetailRepository.bulkUpdateLedgerDetailBalance(
                ledger,
                paymentDate,
                AmountCalculatorByFundType.calculate(fundType, amount)
        );

        return ledgerDetailRepository.save(ledgerDetail);
    }

    @Transactional
    public LedgerDetailInfoView updateLedgerDetail(
            User user,
            Ledger ledger,
            LedgerDetail ledgerDetail,
            UpdateLedgerRequest updateLedgerRequest,
            Integer newAmount
    ) {
        // 1. 장부 상세 내역 업데이트
        ledgerDetail.update(
                user,
                updateLedgerRequest.getStoreInfo(),
                ledgerDetail.getFundType(),
                updateLedgerRequest.getAmount(),
                ledgerDetail.getBalance() + newAmount,
                updateLedgerRequest.getDescription(),
                updateLedgerRequest.getPaymentDate()
        );

        removeLedgerDetail(user.getId(), ledgerDetail.getId());

        LedgerDetail newLedgerDetail = LedgerDetail.of(
                ledger,
                user,
                ledgerDetail.getStoreInfo(),
                ledgerDetail.getFundType(),
                ledgerDetail.getAmount(),
                ledgerDetail.getBalance(),
                ledgerDetail.getDescription(),
                ledgerDetail.getPaymentDate()
        );

        createLedgerDetail(ledger, user, ledgerDetail.getStoreInfo(),
                ledgerDetail.getFundType(),
                ledgerDetail.getAmount(),
                ledgerDetail.getBalance(),
                ledgerDetail.getDescription(),
                ledgerDetail.getPaymentDate());

        // 2. 장부 상세 내역 조회
        final Long ledgerDetailId = ledgerDetail.getId();
        List<LedgerReceipt> ledgerReceipts = ledgerReceiptReader.getLedgerReceipts(ledgerDetailId);
        List<LedgerDocument> ledgerDocuments = ledgerDocumentReader.getLedgerDocuments(ledgerDetailId);

        return LedgerDetailInfoView.of(
                newLedgerDetail,
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

        Integer newAmount = AmountCalculatorByFundType.calculate(ledgerDetail.getFundType(), ledgerDetail.getAmount());

        ledgerDetailRepository.bulkUpdateLedgerDetailBalance(
                ledger,
                ledgerDetail.getPaymentDate(),
                -newAmount
        );

        // update total balance
        ledger.updateTotalBalance(ledger.getTotalBalance() - newAmount);
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
}
