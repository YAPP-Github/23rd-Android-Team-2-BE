package com.moneymong.utils;

import com.moneymong.domain.ledger.entity.enums.FundType;

public class AmountCalculatorByIncomeExpense {
    public static Integer calculate (
            final FundType fundType,
            final Integer amount
    ) {
        final Integer appliedAmount = 2 * amount;
        if(fundType.equals(FundType.EXPENSE)) return -appliedAmount;
        return appliedAmount;
    }
}
