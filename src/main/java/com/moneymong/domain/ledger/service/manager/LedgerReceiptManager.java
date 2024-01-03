package com.moneymong.domain.ledger.service.manager;

import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import com.moneymong.domain.ledger.entity.LedgerReceipt;
import com.moneymong.domain.ledger.repository.LedgerDetailRepository;
import com.moneymong.domain.ledger.repository.LedgerDocumentRepository;
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
    private final LedgerDocumentRepository ledgerDocumentRepository;

    @Transactional
    public List<LedgerReceipt> createReceipts(
            final Long ledgerDetailId,
            final List<String> receiptImageUrls
    ) {
        LedgerDetail ledgerDetail = ledgerDetailRepository
                .findById(ledgerDetailId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_RECEIPT_NOT_FOUND));

        /**
         * p4.ledgerAssembler 호용성을 잘 모르겠는데요. 컴파일러가 최적화를 해주는지 잘 모르겠으나
//         * List -> Stream -> List 변환을 계속 하고 있는 것으로 보여요
         */
//        List<LedgerReceipt> ledgerReceipts = ledgerAssembler
//                .toLedgerReceiptEntities(ledgerDetail, receiptImageUrls);
//
//        return ledgerReceipts.stream()
//                .map(ledgerReceiptRepository::save)
//                .toList();

        /**
         * 1안, 근데 이럴 거면 그냥 cascade를 도입하는 것도 검토해보시면 좋을 것 같아요.
         */
//        return receiptImageUrls.stream()
//                .map(documentImageUrl -> LedgerReceipt.of(ledgerDetail, documentImageUrl))
//                .map(ledgerReceiptRepository::save)
//                .toList();

        /**
         * 작성하다보니 2안이 Query도 깔끔하게 나오겠네요.
         * ledgerAssembler 쓴 버전이랑 쓰지 않은 버전
         */
//        return ledgerReceiptRepository.saveAll(
//                receiptImageUrls.stream()
//                        .map(documentImageUrl -> LedgerReceipt.of(ledgerDetail, documentImageUrl))
//                        .toList()
//        );

        return ledgerReceiptRepository.saveAll(
                ledgerAssembler.toLedgerReceiptEntities(ledgerDetail, receiptImageUrls)
        );

        /**
         * @see com.moneymong.domain.ledger.service.manager.LedgerDocumentManager.createLedgerDocuments()
         * 위에도 같은 이슈
         */
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
