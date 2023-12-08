package com.moneymong.domain.test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
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
