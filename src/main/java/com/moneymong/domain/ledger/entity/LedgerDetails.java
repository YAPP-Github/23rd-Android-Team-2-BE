package com.moneymong.domain.ledger.entity;

import com.moneymong.domain.ledger.entity.enums.FundType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;

@Table(name = "ledger_details")
@Entity
public class LedgerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            targetEntity = Ledger.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "ledger_id",
            referencedColumnName = "id"
    )
    private Ledger ledger;

    @Column(name = "store_info")
    private String storeInfo;

    @Column(name = "fund_type")
    @Enumerated(value = EnumType.STRING)
    private FundType fundType;

    private Integer amount;

    private Integer balance;

    private String description;

    @Column(name = "payment_date")
    private ZonedDateTime paymentDate;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
}
