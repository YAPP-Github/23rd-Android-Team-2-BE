package com.moneymong.domain.agency.entity;

import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "agency_users")
@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE agency_users SET deleted = true where id=?")
public class AgencyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "agency_id",
            referencedColumnName = "id"
    )
    private Agency agency;

    private String userToken;

    @Column(
            name = "agency_user_role",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private AgencyUserRole agencyUserRole;

    @Builder
    private AgencyUser(Long id, Agency agency, String userToken, AgencyUserRole agencyUserRole) {
        this.id = id;
        this.agency = agency;
        this.userToken = userToken;
        this.agencyUserRole = agencyUserRole;
    }
}

