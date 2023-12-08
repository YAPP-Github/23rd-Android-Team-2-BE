package com.moneymong.api.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test")
public class TestEntity {
    @Id
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    String name;


    public static TestEntity createNew(String name) {
        return TestEntity.builder()
                .id(1L)
                .name(name)
                .build();
    }
}