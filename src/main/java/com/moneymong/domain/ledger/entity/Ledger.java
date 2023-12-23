package com.moneymong.domain.ledger.entity;

import com.moneymong.domain.agency.entity.Agency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ledgers")
@Entity
public class Ledger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "agency_id",
            referencedColumnName = "id"
    )
    private Agency agency;

    @Column(name = "total_balance")
    private Integer totalBalance;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    public void updateTotalBalance(final Integer amount) {
        this.totalBalance = amount;
        this.updatedAt = ZonedDateTime.now();
    }


    public static Ledger of(
            final Agency agency,
            final Integer totalBalance
    ) {
        return Ledger.builder()
                .agency(agency)
                .totalBalance(totalBalance)
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
    }
}
