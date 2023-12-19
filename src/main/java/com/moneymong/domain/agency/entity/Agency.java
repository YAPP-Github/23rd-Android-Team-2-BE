package com.moneymong.domain.agency.entity;

import com.moneymong.domain.agency.entity.enums.AgencyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static lombok.AccessLevel.PROTECTED;

@Table(name = "agencies")
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE agencies SET deleted = true where id=?")
public class Agency {

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
}
