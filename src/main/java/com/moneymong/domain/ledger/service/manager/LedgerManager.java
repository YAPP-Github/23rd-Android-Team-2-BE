package com.moneymong.domain.ledger.service.manager;

import com.moneymong.domain.agency.entity.AgencyUser;
import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
import com.moneymong.domain.agency.service.AgencyService;
import com.moneymong.domain.ledger.api.request.CreateLedgerRequest;
import com.moneymong.domain.ledger.api.response.LedgerDetailInfoView;
import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import com.moneymong.domain.ledger.entity.LedgerReceipt;
import com.moneymong.domain.ledger.entity.enums.FundType;
import com.moneymong.domain.ledger.repository.LedgerRepository;
import com.moneymong.domain.user.entity.User;
import com.moneymong.domain.user.service.UserService;
import com.moneymong.global.constant.MoneymongConstant;
import com.moneymong.global.exception.custom.BadRequestException;
import com.moneymong.global.exception.custom.InvalidAccessException;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import com.moneymong.utils.AmountCalculatorByFundType;
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
    private final LedgerReceiptManager ledgerReceiptManager;
    private final LedgerDocumentManager ledgerDocumentManager;
    private final LedgerRepository ledgerRepository;


    @Transactional
    public LedgerDetailInfoView createLedger(
            final Long userId,
            final CreateLedgerRequest createLedgerRequest
    ) {
        // 1. 유저 검증
        User user = userService.validateUser(userId);

        // 2. 유저 소속 검증
        AgencyUser agencyUser = agencyService.validateAgencyUser(userId);

        // 3. 소속 장부 검증
        Ledger ledger = validateLedger(agencyUser.getAgency().getId());

        // 4. 유저 권한 검증
        if(!agencyUser.getAgencyUserRole().equals(AgencyUserRole.STAFF)) {
            throw new InvalidAccessException(ErrorCode.INVALID_LEDGER_ACCESS);
        }

        // 5. 장부 totalBalance 업데이트
        Ledger updateLedger = updateLedgerTotalBalance(
                createLedgerRequest.getFundType(),
                createLedgerRequest.getAmount(),
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
        if(requestReceiptImageUrls.size() > 0) {
            ledgerReceipts = ledgerReceiptManager.createLedgerReceipts(updateLedger, requestReceiptImageUrls);
        }


        // 8. 장부 증빙 자료 등록
        List<LedgerDocument> ledgerDocuments = List.of();
        List<String> requestDocumentImageUrls = createLedgerRequest.getDocumentImageUrls();
        if(requestDocumentImageUrls.size() > 0) {
            ledgerDocuments = ledgerDocumentManager.createLedgerDocuments(updateLedger, requestDocumentImageUrls);
        }
        return LedgerDetailInfoView.of(ledgerDetail, ledgerReceipts, ledgerDocuments);
    }

    private Ledger updateLedgerTotalBalance(
            final FundType fundType,
            final Integer amount,
            final Ledger ledger
    ) {
        Integer newAmount = AmountCalculatorByFundType.calculate(fundType, amount);

        // 7. 장부 금액 초과 검증
        if(ledger.getTotalBalance() + newAmount > MoneymongConstant.MAX_ALLOWED_AMOUNT) {
            throw new BadRequestException(ErrorCode.INVALID_LEDGER_AMOUNT);
        }

        ledger.updateTotalBalance(newAmount);
        return ledgerRepository.save(ledger);
    }


    private Ledger validateLedger(final Long agencyId) {
        return ledgerRepository
                .findByAgencyId(agencyId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_NOT_FOUND));
    }
}
