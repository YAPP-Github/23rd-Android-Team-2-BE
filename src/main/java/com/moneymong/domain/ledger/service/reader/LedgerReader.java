package com.moneymong.domain.ledger.service.reader;

import com.moneymong.domain.ledger.api.response.ledger.LedgerInfoView;
import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.repository.LedgerDetailRepository;
import com.moneymong.domain.ledger.repository.LedgerRepository;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LedgerReader {
    private final LedgerRepository ledgerRepository;
    private final LedgerDetailRepository ledgerDetailRepository;

    @Transactional(readOnly = true)
    public LedgerInfoView search(
            final Long ledgerId,
            final Integer year,
            final Integer month,
            final Integer page,
            final Integer limit
    ) {
        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(
                () -> new NotFoundException(ErrorCode.LEDGER_NOT_FOUND)
        );

        ZonedDateTime from = ZonedDateTime.of(year, month, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime to = from.plusMonths(1);
        List<LedgerDetail> ledgerDetailPage = ledgerDetailRepository.search(
                ledger,
                from,
                to,
                PageRequest.of(page, limit)
        );

        return LedgerInfoView.from(ledger, ledgerDetailPage);
    }
}
