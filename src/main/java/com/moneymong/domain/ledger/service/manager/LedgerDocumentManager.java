package com.moneymong.domain.ledger.service.manager;

import com.moneymong.domain.agency.entity.AgencyUser;
import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
import com.moneymong.domain.agency.repository.AgencyUserRepository;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import com.moneymong.domain.ledger.repository.LedgerDetailRepository;
import com.moneymong.domain.ledger.repository.LedgerDocumentRepository;
import com.moneymong.domain.ledger.service.mapper.LedgerAssembler;
import com.moneymong.domain.user.entity.User;
import com.moneymong.domain.user.repository.UserRepository;
import com.moneymong.global.exception.custom.InvalidAccessException;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerDocumentManager {

    private final LedgerAssembler ledgerAssembler;
    private final UserRepository userRepository;
    private final AgencyUserRepository agencyUserRepository;
    private final LedgerDetailRepository ledgerDetailRepository;
    private final LedgerDocumentRepository ledgerDocumentRepository;

    @Transactional
    public List<LedgerDocument> createLedgerDocuments(
            final Long ledgerDetailId,
            final List<String> documentImageUrls
    ) {
        LedgerDetail ledgerDetail = ledgerDetailRepository
                .findById(ledgerDetailId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_RECEIPT_NOT_FOUND));

        List<LedgerDocument> ledgerDocuments = ledgerAssembler
                .toLedgerDocumentEntities(ledgerDetail, documentImageUrls);

        return ledgerDocuments.stream()
                .map(ledgerDocumentRepository::save)
                .toList();
    }

    @Transactional
    public void removeLedgerDocuments(
            final Long userId,
            final Long ledgerDetailId,
            final Long documentId
    ) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        LedgerDetail ledgerDetail = ledgerDetailRepository
                .findById(ledgerDetailId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_RECEIPT_NOT_FOUND));

        // === 소속 ===
        AgencyUser agencyUser = agencyUserRepository
                .findByUserIdAndAgencyId(user.getId(), ledgerDetail.getLedger().getAgency().getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.AGENCY_NOT_FOUND));

        // === 권한 ===
        if (!agencyUser.getAgencyUserRole().equals(AgencyUserRole.STAFF)) {
            throw new InvalidAccessException(ErrorCode.INVALID_LEDGER_ACCESS);
        }

        LedgerDocument ledgerDocument = ledgerDocumentRepository
                .findById(documentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_DOCUUMENT_NOT_FOUND));

        ledgerDocumentRepository.delete(ledgerDocument);
    }
}
