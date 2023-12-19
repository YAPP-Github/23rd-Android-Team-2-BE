package com.moneymong.domain.ledger.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.List;
import org.hibernate.annotations.BatchSize;

@Table(name = "ledgers")
@Entity
public class Ledger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agency_id")
    private Long agencyId; // 소속 그룹 엔티티 생성 이 후 연관 관계 매핑

    @BatchSize(size = 50)
    @OneToMany(
            mappedBy = "ledger",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LedgerDetails> ledgerDetailsList;

    @BatchSize(size = 50)
    @OneToMany(
            mappedBy = "ledger",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LedgerReceipts> ledgerReceiptsList;

    @Column(name = "total_balance")
    private Integer totalBalance;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
}
