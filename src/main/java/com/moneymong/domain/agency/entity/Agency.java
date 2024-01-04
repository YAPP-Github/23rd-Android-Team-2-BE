package com.moneymong.domain.agency.entity;

import com.moneymong.domain.agency.entity.enums.AgencyType;
import com.moneymong.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Table(name = "agencies")
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE agencies SET deleted = true where id=?")
public class Agency extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "agency_name",
            length = 100,
            nullable = false
    )
    private String agencyName;

    @Column(
            name = "agency_type",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private AgencyType agencyType;

    @OneToMany(mappedBy = "agency", cascade = CascadeType.PERSIST)
    private List<AgencyUser> agencyUsers = new ArrayList<>();

    @Column(
            name = "head_count",
            nullable = false
    )
    private Integer headCount;

    @Column(
            name = "thumbnail_image_url",
            length = 2000
    )
    private String thumbnailImageUrl;

    private String description;

    @Builder
    private Agency(Long id, String agencyName, AgencyType agencyType, Integer headCount, String thumbnailImageUrl, String description) {
        this.id = id;
        this.agencyName = agencyName;
        this.agencyType = agencyType;
        this.headCount = headCount;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.description = description;
    }

    public static Agency of(String agencyName, AgencyType agencyType, String thumbnailImageUrl, String description) {
        return Agency.builder()
                .agencyName(agencyName)
                .agencyType(agencyType)
                .thumbnailImageUrl(thumbnailImageUrl)
                .description(description)
                .headCount(1)
                .build();
    }

    public void addAgencyUser(AgencyUser agencyUser) {
        this.agencyUsers.add(agencyUser);
    }
}
