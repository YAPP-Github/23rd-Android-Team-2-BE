package com.moneymong.domain.ledger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ledger_documents")
@Entity
public class LedgerDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ledger_detail_id",
            referencedColumnName = "id"
    )
    private LedgerDetail ledgerDetail;

    @Column(
            name = "document_image_url",
            length = 2500
    )
    private String documentImageUrl;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    public static LedgerDocument of(
            final LedgerDetail ledgerDetail,
            final String documentImageUrl
    ) {
        return LedgerDocument
                .builder()
                .ledgerDetail(ledgerDetail)
                .documentImageUrl(documentImageUrl)
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
    }
}
