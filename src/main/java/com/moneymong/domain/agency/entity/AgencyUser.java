package com.moneymong.domain.agency.entity;

import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Table(name = "agency_users")
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE agency_users SET deleted = true where id=?")
public class AgencyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(
            name = "agency_id",
            referencedColumnName = "id"
    )
    private Agency agency;

    @Column(nullable = false)
    private String userToken;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AgencyUserRole agencyUserRole;
}
