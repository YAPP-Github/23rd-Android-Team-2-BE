package com.moneymong.domain.test.repository;

import com.moneymong.domain.test.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
