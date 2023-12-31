package com.moneymong.utils;

import com.moneymong.domain.ledger.entity.enums.FundType;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ModificationAmountCalculator {
    public static Integer calculate(
            final FundType fundType,
            final Integer amount,
            final Integer newAmount
    ) {
        BigDecimal undoneAmount = new BigDecimal(-1L * AmountCalculatorByFundType.calculate(fundType, amount));
        BigDecimal appliedAmount = new BigDecimal(AmountCalculatorByFundType.calculate(fundType, newAmount));

        return undoneAmount.add(appliedAmount).intValue();
    }
}
