package com.moneymong.domain.ledger.service.reader;

import com.moneymong.domain.ledger.api.response.LedgerDetailInfoView;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import com.moneymong.domain.ledger.entity.LedgerReceipt;
import com.moneymong.domain.ledger.repository.LedgerDetailRepository;
import com.moneymong.domain.user.entity.User;
import com.moneymong.domain.user.repository.UserRepository;
import com.moneymong.domain.user.service.UserService;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerDetailReader {
    private final LedgerReceiptReader ledgerReceiptReader;
    private final LedgerDocumentReader ledgerDocumentReader;
    private final UserRepository userRepository;
    private final LedgerDetailRepository ledgerDetailRepository;

    @Transactional(readOnly = true)
    public LedgerDetailInfoView getLedgerDetail(
            final Long userId,
            final Long ledgerDetailId
    ) {

        // 1. 유저 검증
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // 2. 장부 상세 내역 검증
        LedgerDetail ledgerDetail = ledgerDetailRepository
                .findById(ledgerDetailId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_DETAIL_NOT_FOUND));

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
}
