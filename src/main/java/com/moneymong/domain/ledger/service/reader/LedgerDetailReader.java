package com.moneymong.domain.ledger.service.reader;

import com.moneymong.domain.agency.entity.AgencyUser;
import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
import com.moneymong.domain.ledger.api.response.LedgerDetailInfoView;
import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import com.moneymong.domain.ledger.entity.LedgerReceipt;
import com.moneymong.domain.ledger.repository.LedgerDetailRepository;
import com.moneymong.domain.ledger.repository.LedgerDocumentRepository;
import com.moneymong.domain.ledger.repository.LedgerReceiptRepository;
import com.moneymong.domain.user.entity.User;
import com.moneymong.domain.user.service.UserService;
import com.moneymong.global.exception.custom.InvalidAccessException;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerDetailReader {
    private final UserService userService;
    private final LedgerDetailRepository ledgerDetailRepository;
    private final LedgerReceiptReader ledgerReceiptReader;
    private final LedgerDocumentReader ledgerDocumentReader;


    @Transactional(readOnly = true)
    public LedgerDetailInfoView getLedgerDetail(
            final Long userId,
            final Long ledgerDetailId
    ) {

        // 1. 유저 검증
        User user = userService.validateUser(userId);

        // 2. 장부 상세 내역 검증
        LedgerDetail ledgerDetail = validateLedgerDetail(ledgerDetailId);


        // 3. LedgerId -> 장부 상세 내역 조회
        List<LedgerReceipt> ledgerReceipts = ledgerReceiptReader.getLedgerReceipts(ledgerDetailId);
        List<LedgerDocument> ledgerDocuments = ledgerDocumentReader.getLedgerDocuments(ledgerDetailId);

        return LedgerDetailInfoView.of(
                ledgerDetail,
                ledgerReceipts,
                ledgerDocuments,
                ledgerDetail.getUser()
        );
    }

    public LedgerDetail validateLedgerDetail(final Long id) {
        return ledgerDetailRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_DETAIL_NOT_FOUND));
    }
}
