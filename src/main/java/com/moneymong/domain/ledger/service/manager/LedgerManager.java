package com.moneymong.domain.ledger.service.manager;

import com.moneymong.domain.agency.entity.AgencyUser;
import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
import com.moneymong.domain.agency.service.AgencyService;
import com.moneymong.domain.ledger.api.request.CreateLedgerRequest;
import com.moneymong.domain.ledger.api.request.UpdateLedgerRequest;
import com.moneymong.domain.ledger.api.response.LedgerDetailInfoView;
import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import com.moneymong.domain.ledger.entity.LedgerReceipt;
import com.moneymong.domain.ledger.repository.LedgerRepository;
import com.moneymong.domain.ledger.service.reader.LedgerDetailReader;
import com.moneymong.domain.user.entity.User;
import com.moneymong.domain.user.service.UserService;
import com.moneymong.global.constant.MoneymongConstant;
import com.moneymong.global.exception.custom.BadRequestException;
import com.moneymong.global.exception.custom.InvalidAccessException;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import com.moneymong.utils.AmountCalculatorByFundType;
import com.moneymong.utils.AmountCalculatorByIncomeExpense;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerManager {
    private final UserService userService;
    private final AgencyService agencyService;
    private final LedgerDetailManager ledgerDetailManager;
    private final LedgerDetailReader ledgerDetailReader;
    private final LedgerReceiptManager ledgerReceiptManager;
    private final LedgerDocumentManager ledgerDocumentManager;
    private final LedgerRepository ledgerRepository;


    @Transactional
    public LedgerDetailInfoView createLedger(
            final Long userId,
            final Long ledgerId,
            final CreateLedgerRequest createLedgerRequest
    ) {
        // 1. 유저 검증
        User user = userService.validateUser(userId);

        // 2. 소속 장부 검증
        Ledger ledger = validateLedger(ledgerId);

        // 3. 유저 소속 검증
        AgencyUser agencyUser = agencyService.validateAgencyUser(userId, ledger.getAgency().getId());


        // 4. 유저 권한 검증
        if (!agencyUser.getAgencyUserRole().equals(AgencyUserRole.STAFF)) {
            throw new InvalidAccessException(ErrorCode.INVALID_LEDGER_ACCESS);
        }

        // 5. 장부 totalBalance 업데이트
        Ledger updateLedger = updateLedgerTotalBalance(
                AmountCalculatorByFundType.calculate(
                        createLedgerRequest.getFundType(),
                        createLedgerRequest.getAmount()
                ),
                ledger
        );

        // 6. 장부 내역 등록
        LedgerDetail ledgerDetail = ledgerDetailManager.createLedgerDetail(
                updateLedger,
                user,
                createLedgerRequest.getStoreInfo(),
                createLedgerRequest.getFundType(),
                createLedgerRequest.getAmount(),
                updateLedger.getTotalBalance(),
                createLedgerRequest.getDescription(),
                createLedgerRequest.getPaymentDate()
        );

        // 7. 장부 영수증 등록
        List<LedgerReceipt> ledgerReceipts = List.of();
        List<String> requestReceiptImageUrls = createLedgerRequest.getReceiptImageUrls();
        if (requestReceiptImageUrls.size() > 0) {
            ledgerReceipts = ledgerReceiptManager.createLedgerReceipts(ledgerDetail, requestReceiptImageUrls);
        }

        // 8. 장부 증빙 자료 등록
        List<LedgerDocument> ledgerDocuments = List.of();
        List<String> requestDocumentImageUrls = createLedgerRequest.getDocumentImageUrls();
        if (requestDocumentImageUrls.size() > 0) {
            ledgerDocuments = ledgerDocumentManager.createLedgerDocuments(ledgerDetail, requestDocumentImageUrls);
        }
        return LedgerDetailInfoView.of(
                ledgerDetail,
                ledgerReceipts,
                ledgerDocuments,
                user
        );
    }

    @Transactional
    public LedgerDetailInfoView updateLedger(
            final Long userId,
            final Long ledgerId,
            final Long ledgerDetailId,
            final UpdateLedgerRequest updateLedgerRequest
    ) {
        // 1. 유저 검증
        User user = userService.validateUser(userId);

        // 2. 소속 장부 검증
        Ledger ledger = validateLedger(ledgerId);

        // 3. 유저 소속 검증
        AgencyUser agencyUser = agencyService.validateAgencyUser(userId, ledger.getAgency().getId());

        // 4. 유저 권한 검증
        if (!agencyUser.getAgencyUserRole().equals(AgencyUserRole.STAFF)) {
            throw new InvalidAccessException(ErrorCode.INVALID_LEDGER_ACCESS);
        }

        // 5. 장부 상세 내역 검증
        LedgerDetail ledgerDetail = ledgerDetailReader.validateLedgerDetail(ledgerDetailId);


        // 6. 장부 총 잔액 업데이트
        if (!ledgerDetail.getFundType().equals(updateLedgerRequest.getFundType())) {
            final Integer newAmount = AmountCalculatorByIncomeExpense.calculate(
                    updateLedgerRequest.getFundType(),
                    updateLedgerRequest.getAmount()
            );

            ledger = updateLedgerTotalBalance(newAmount, ledger);
        }

        // 7. 장부 상세 내역 정보 업데이트
        return ledgerDetailManager.updateLedgerDetail(
                user,
                ledger,
                ledgerDetail,
                updateLedgerRequest
        );
    }

    private Ledger updateLedgerTotalBalance(
            final Integer newAmount,
            final Ledger ledger
    ) {

        final Integer expectedAmount = ledger.getTotalBalance() + newAmount;

        // 7. 장부 금액 최소 초과 검증
        if (expectedAmount < MoneymongConstant.MIN_ALLOWED_AMOUNT &&
            expectedAmount > MoneymongConstant.MAX_ALLOWED_AMOUNT
        ) {
            throw new BadRequestException(ErrorCode.INVALID_LEDGER_AMOUNT);
        }

        ledger.updateTotalBalance(newAmount);
        return ledgerRepository.save(ledger);
    }


    private Ledger validateLedger(final Long id) {
        return ledgerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_NOT_FOUND));
    }
}
