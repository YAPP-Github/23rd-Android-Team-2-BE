package com.moneymong.domain.ledger.entity;

import com.moneymong.domain.ledger.entity.enums.LedgerUserRoleType;
import com.moneymong.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;

@Table(name = "ledger_users")
@Entity
public class LedgerUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            targetEntity = Ledger.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "ledger_id",
            referencedColumnName = "id"
    )
    private Ledger ledger;

    @ManyToOne(
            targetEntity = User.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

    @Column(name = "user_role")
    @Enumerated(value = EnumType.STRING)
    private LedgerUserRoleType userRole;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
}
