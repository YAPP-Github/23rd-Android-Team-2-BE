package com.moneymong.domain.ledger.api.response;

import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import com.moneymong.domain.ledger.entity.LedgerReceipt;
import com.moneymong.domain.ledger.entity.enums.FundType;
import com.moneymong.domain.user.entity.User;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.text.DateFormatter;
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
    private String authorName;

    public static LedgerDetailInfoView of(
            final LedgerDetail ledgerDetail,
            final List<LedgerReceipt> ledgerReceipts,
            final List<LedgerDocument> ledgerDocuments,
            final User user
    ) {
        return LedgerDetailInfoView
                .builder()
                .id(ledgerDetail.getId())
                .storeInfo(ledgerDetail.getStoreInfo())
                .amount(ledgerDetail.getAmount())
                .fundType(ledgerDetail.getFundType())
                .description(ledgerDetail.getDescription())
                .paymentDate(ledgerDetail.getPaymentDate())
                .receiptImageUrls(
                        ledgerReceipts
                                .stream()
                                .map(ledgerReceipt -> LedgerReceiptInfoView.from(ledgerReceipt.getId(),
                                        ledgerReceipt.getReceiptImageUrl()))
                                .toList()
                )
                .documentImageUrls(
                        ledgerDocuments
                                .stream()
                                .map(ledgerDocument -> LedgerDocumentInfoView.from(ledgerDocument.getId(),
                                        ledgerDocument.getDocumentImageUrl()))
                                .toList()
                )
                .authorName(user.getNickname())
                .build();
    }
}
