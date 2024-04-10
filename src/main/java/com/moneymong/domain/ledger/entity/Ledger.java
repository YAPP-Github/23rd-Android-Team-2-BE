package com.moneymong.domain.ledger.entity;

import com.moneymong.domain.agency.entity.Agency;
import com.moneymong.global.domain.TimeBaseEntity;
import com.moneymong.global.exception.custom.BadRequestException;
import com.moneymong.global.exception.enums.ErrorCode;
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

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ledgers")
@Entity
public class Ledger extends TimeBaseEntity {

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

    public void updateTotalBalance(int newAmount) {
        BigDecimal expectedAmount = new BigDecimal(this.getTotalBalance())
                .add(new BigDecimal(newAmount));

        // 장부 금액 최소 초과 검증
        BigDecimal minValue = new BigDecimal("-999999999");
        BigDecimal maxValue = new BigDecimal("999999999");
        if (!(expectedAmount.compareTo(minValue) >= 0 &&
                expectedAmount.compareTo(maxValue) <= 0)
        ) {
            throw new BadRequestException(ErrorCode.INVALID_LEDGER_AMOUNT);
        }

        this.totalBalance = expectedAmount.intValue();
    }

    public static Ledger of(
            final Agency agency,
            final Integer totalBalance
    ) {
        return Ledger.builder()
                .agency(agency)
                .totalBalance(totalBalance)
                .build();
    }
}
