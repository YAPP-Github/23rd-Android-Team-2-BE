package com.moneymong.domain.ledger.api.response;

import com.moneymong.domain.ledger.entity.LedgerDetails;
import com.moneymong.domain.ledger.entity.LedgerDocuments;
import com.moneymong.domain.ledger.entity.LedgerReceipts;
import com.moneymong.domain.ledger.entity.enums.FundType;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LedgerDetailInfoView {
    private Long id;
    private String storeInfo;
    private Integer amount;
    private FundType fundType;
    private String description;
    private ZonedDateTime paymentDate;
    private List<LedgerReceiptInfoView> receiptImageUrls;
    private List<LedgerDocumentInfoView> documentImageUrls;

    public static LedgerDetailInfoView of(
            LedgerDetails ledgerDetails,
            List<LedgerReceipts> ledgerReceipts,
            List<LedgerDocuments> ledgerDocuments
    ) {
        return LedgerDetailInfoView
                .builder()
                .id(ledgerDetails.getId())
                .storeInfo(ledgerDetails.getStoreInfo())
                .amount(ledgerDetails.getAmount())
                .fundType(ledgerDetails.getFundType())
                .description(ledgerDetails.getDescription())
                .paymentDate(ledgerDetails.getPaymentDate())
                .receiptImageUrls(
                        ledgerReceipts
                                .stream()
                                .map(ledgerReceipt -> LedgerReceiptInfoView.from(ledgerReceipt.getId(), ledgerReceipt.getReceiptImageUrl()))
                                .toList()
                )
                .documentImageUrls(
                        ledgerDocuments
                                .stream()
                                .map(ledgerDocument -> LedgerDocumentInfoView.from(ledgerDocument.getId(), ledgerDocument.getDocumentImageUrl()))
                                .toList()
                )
                .build();
    }
}
