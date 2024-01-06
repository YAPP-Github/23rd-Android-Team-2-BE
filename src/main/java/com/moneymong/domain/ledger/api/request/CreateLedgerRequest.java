package com.moneymong.domain.ledger.api.request;

import com.moneymong.domain.ledger.entity.enums.FundType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLedgerRequest {
    @NotBlank(message = "storeInfo를 입력해주세요.")
    @Length(min = 1, max = 20, message = "storeInfo 1자 - 20자 입력해주세요.")
    private String storeInfo;

    @NotNull(message = "fundType(INCOME, EXPENSE)를 입력해주세요.")
    private FundType fundType;

    @NotNull(message = "amount를 입력해주세요.")
    private Integer amount;

    @NotBlank(message = "description를 입력해주세요.")
    private String description;

    @NotNull(message = "paymentDate를 입력해주세요.")
    private ZonedDateTime paymentDate;

    @Size(min = 1, max = 12, message = "영수증 1개 - 12개 입력해주세요.")
    private List<String> receiptImageUrls;

    @Size(min = 1, max = 12, message = "증빙 자료 1개 - 12개 입력해주세요.")
    private List<String> documentImageUrls;
}