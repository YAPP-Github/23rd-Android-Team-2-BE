package com.moneymong.domain.agency.entity;

import com.moneymong.domain.agency.entity.enums.AgencyUserRole;
import com.moneymong.global.domain.BaseEntity;
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
import org.springframework.util.Assert;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "agency_users")
@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE agency_users SET deleted = true where id=?")
public class AgencyUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "agency_id",
            referencedColumnName = "id"
    )
    private Agency agency;

    @Column(name = "user_id")
    private Long userId;

    @Column(
            name = "agency_user_role",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private AgencyUserRole agencyUserRole;

    @Builder
    private AgencyUser(Long id, Agency agency, Long userId, AgencyUserRole agencyUserRole) {
        this.id = id;
        this.agency = agency;
        this.userId = userId;
        this.agencyUserRole = agencyUserRole;
    }

    public static AgencyUser of(Agency agency, Long userId, AgencyUserRole agencyUserRole) {
        return AgencyUser.builder()
                .agency(agency)
                .userId(userId)
                .agencyUserRole(agencyUserRole)
                .build();
    }

    public void updateAgencyUserRole(AgencyUserRole role) {
        Assert.notNull(role, "변경할 권한을 입력해주세요.");
        this.agencyUserRole = role;
    }
}

