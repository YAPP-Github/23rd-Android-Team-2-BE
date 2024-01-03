package com.moneymong.utils;

import com.moneymong.domain.ledger.entity.enums.FundType;

import java.util.Objects;

public class AmountCalculatorByFundType {
    public static Integer calculate (
            final FundType fundType,
            /**
             * p4. Utils 클래스에서 Wrapper Class를 사용하면 Boxing 오버헤드가 걱정되긴 합니다.
             * 사용처를 명확하게 몰라서 일단 p4로
            */
            final Integer amount
    ) {

        /**
         * p4. equals 메소드 사용하 실 때는 Not Null 객체를 앞에 두면 좋습니다. (NPE 방지)
         *     Objects.equals 를 사용 하는 것도 방법입니다.
         *     ex)
         *     if(Objects.equals(FundType.EXPENSE, fundType))
         */
        if(FundType.EXPENSE.equals(fundType)) return -amount;
        return amount;
    }
}
