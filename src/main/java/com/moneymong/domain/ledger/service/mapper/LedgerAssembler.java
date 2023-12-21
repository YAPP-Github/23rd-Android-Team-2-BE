package com.moneymong.domain.ledger.service.mapper;

import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetails;
import com.moneymong.domain.ledger.entity.LedgerDocuments;
import com.moneymong.domain.ledger.entity.LedgerReceipts;
import com.moneymong.domain.ledger.entity.enums.FundType;
import com.moneymong.domain.user.entity.User;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LedgerAssembler {

    public LedgerDetails toLedgerDetailEntity(
            final Ledger ledger,
            final User user,
            final String storeInfo,
            final FundType fundType,
            final Integer amount,
            final Integer balance,
            final String description,
            final ZonedDateTime paymentDate

    ) {
        return LedgerDetails.of(ledger, user, storeInfo, fundType, amount, balance, description, paymentDate);
    }

    public List<LedgerReceipts> toLedgerReceiptEntities(
            final Ledger ledger,
            final List<String> receiptImageUrls
    ) {
        return receiptImageUrls
                .stream()
                .map(receiptImageUrl -> LedgerReceipts.of(ledger, receiptImageUrl))
                .toList();
    }

    public List<LedgerDocuments> toLedgerDocumentEntities(
            final Ledger ledger,
            final List<String> documentImageUrls
    ) {
        return documentImageUrls
                .stream()
                .map(documentImageUrl -> LedgerDocuments.of(ledger, documentImageUrl))
                .toList();
    }
}
