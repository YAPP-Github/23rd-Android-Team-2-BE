package com.moneymong.domain.ledger.entity;

import com.moneymong.global.domain.TimeBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
public class LedgerDocument extends TimeBaseEntity {

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

    public static LedgerDocument of(
            final LedgerDetail ledgerDetail,
            final String documentImageUrl
    ) {
        return LedgerDocument
                .builder()
                .ledgerDetail(ledgerDetail)
                .documentImageUrl(documentImageUrl)
                .build();
    }
}
