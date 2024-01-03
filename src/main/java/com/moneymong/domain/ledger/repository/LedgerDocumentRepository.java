package com.moneymong.domain.ledger.repository;

import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerDocumentRepository extends JpaRepository<LedgerDocument, Long> {
    List<LedgerDocument> findByLedgerDetailId(final Long ledgerDetailId);

    /**
     * p2.이거 아마 전체 조회 한 뒤에 row 수만큼 delete 문이 발생 할 겁니다.
     * https://traeper.tistory.com/208
     */
    void deleteByLedgerDetail(final LedgerDetail ledgerDetail);
}
