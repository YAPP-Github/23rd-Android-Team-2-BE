package com.moneymong.api.service;

import com.moneymong.api.domain.TestEntity;
import com.moneymong.api.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TestService {
    private final TestRepository testRepository;

    @Transactional
    public TestEntity create(String name) {
        TestEntity testEntity = TestEntity.createNew(name);
        return testRepository.save(testEntity);
    }
}