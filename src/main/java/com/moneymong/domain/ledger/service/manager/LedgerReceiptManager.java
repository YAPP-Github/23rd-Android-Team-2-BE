package com.moneymong.domain.ledger.service.manager;

import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerReceipt;
import com.moneymong.domain.ledger.repository.LedgerDetailRepository;
import com.moneymong.domain.ledger.repository.LedgerReceiptRepository;
import com.moneymong.domain.ledger.service.mapper.LedgerAssembler;
import com.moneymong.domain.user.service.UserService;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerReceiptManager {
    private final UserService userService;
    private final LedgerAssembler ledgerAssembler;
    private final LedgerDetailRepository ledgerDetailRepository;
    private final LedgerReceiptRepository ledgerReceiptRepository;

    @Transactional
    public List<LedgerReceipt> createReceipts(
            final Long ledgerDetailId,
            final List<String> receiptImageUrls
    ) {
        LedgerDetail ledgerDetail = ledgerDetailRepository
                .findById(ledgerDetailId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_RECEIPT_NOT_FOUND));

        List<LedgerReceipt> ledgerReceipts = ledgerAssembler
                .toLedgerReceiptEntities(ledgerDetail, receiptImageUrls);

        return ledgerReceipts.stream()
                .map(ledgerReceiptRepository::save)
                .toList();
    }


    @Transactional
    public void removeReceipt(
            final Long ledgerDetailId,
            final Long receiptId
    ) {

        LedgerDetail ledgerDetail = ledgerDetailRepository
                .findById(ledgerDetailId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_DETAIL_NOT_FOUND));

        LedgerReceipt ledgerReceipt = ledgerReceiptRepository
                .findById(receiptId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_RECEIPT_NOT_FOUND));

        ledgerReceiptRepository.delete(ledgerReceipt);
    }
}
