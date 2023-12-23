package com.moneymong.domain.ledger.service.manager;

import com.moneymong.domain.ledger.api.request.UpdateLedgerRequest;
import com.moneymong.domain.ledger.api.response.LedgerDetailInfoView;
import com.moneymong.domain.ledger.api.response.LedgerDocumentInfoView;
import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import com.moneymong.domain.ledger.entity.LedgerReceipt;
import com.moneymong.domain.ledger.entity.enums.FundType;
import com.moneymong.domain.ledger.repository.LedgerDetailRepository;
import com.moneymong.domain.ledger.service.mapper.LedgerAssembler;
import com.moneymong.domain.ledger.service.reader.LedgerDocumentReader;
import com.moneymong.domain.ledger.service.reader.LedgerReceiptReader;
import com.moneymong.domain.user.entity.User;
import com.moneymong.utils.AmountCalculatorByFundType;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerDetailManager {
    private final LedgerAssembler ledgerAssembler;
    private final LedgerDetailRepository ledgerDetailRepository;
    private final LedgerReceiptReader ledgerReceiptReader;
    private final LedgerDocumentReader ledgerDocumentReader;

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

        return ledgerDetailRepository.save(ledgerDetail);
    }

    @Transactional
    public LedgerDetailInfoView updateLedgerDetail(
            final User user,
            final Ledger ledger,
            final LedgerDetail ledgerDetail,
            final UpdateLedgerRequest updateLedgerRequest
    ) {
        // 1. 장부 상세 내역 업데이트
        ledgerDetail.update(
                user,
                updateLedgerRequest.getStoreInfo(),
                updateLedgerRequest.getFundType(),
                updateLedgerRequest.getAmount(),
                ledger.getTotalBalance(),
                updateLedgerRequest.getDescription(),
                updateLedgerRequest.getPaymentDate()
        );

        LedgerDetail newLedgerDetail = ledgerDetailRepository.save(ledgerDetail);

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
}
