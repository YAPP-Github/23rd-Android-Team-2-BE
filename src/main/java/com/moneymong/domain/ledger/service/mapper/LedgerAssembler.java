package com.moneymong.domain.ledger.service.mapper;

import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import com.moneymong.domain.ledger.entity.LedgerReceipt;
import com.moneymong.domain.ledger.entity.enums.FundType;
import com.moneymong.domain.user.entity.User;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LedgerAssembler {

    public LedgerDetail toLedgerDetailEntity(
            final Ledger ledger,
            final User user,
            final String storeInfo,
            final FundType fundType,
            final Integer amount,
            final Integer balance,
            final String description,
            final ZonedDateTime paymentDate

    ) {
        return LedgerDetail.of(
                ledger,
                user,
                storeInfo,
                fundType,
                amount,
                balance,
                description,
                paymentDate
        );
    }

    public List<LedgerReceipt> toLedgerReceiptEntities(
            final LedgerDetail ledgerDetail,
            final List<String> receiptImageUrls
    ) {
        return receiptImageUrls
                .stream()
                .map(receiptImageUrl -> LedgerReceipt.of(ledgerDetail, receiptImageUrl))
                .toList();
    }

    public List<LedgerDocument> toLedgerDocumentEntities(
            final LedgerDetail ledgerDetail,
            final List<String> documentImageUrls
    ) {
        return documentImageUrls
                .stream()
                .map(documentImageUrl -> LedgerDocument.of(ledgerDetail, documentImageUrl))
                .toList();
    }
}
