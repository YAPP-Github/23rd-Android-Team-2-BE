package com.moneymong.domain.ledger.entity;

import com.moneymong.domain.ledger.entity.enums.FundType;
import com.moneymong.domain.user.entity.User;
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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ledger_details")
@Entity
public class LedgerDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ledger_id",
            referencedColumnName = "id"
    )
    private Ledger ledger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

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


    public static LedgerDetail of(
            final Ledger ledger,
            final User user,
            final String storeInfo,
            final FundType fundType,
            final Integer amount,
            final Integer balance,
            final String description,
            final ZonedDateTime paymentDate
    ) {
        return LedgerDetail
                .builder()
                .ledger(ledger)
                .user(user)
                .storeInfo(storeInfo)
                .fundType(fundType)
                .amount(amount)
                .balance(balance)
                .description(description)
                .paymentDate(paymentDate)
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
    }
}
