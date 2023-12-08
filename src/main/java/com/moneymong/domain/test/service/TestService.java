package com.moneymong.domain.test.service;

import com.moneymong.domain.test.TestEntity;
import com.moneymong.domain.test.repository.TestRepository;
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

    @Transactional(readOnly = true)
    public TestEntity find(Long id) {
        return testRepository.findById(id)
                .orElseThrow();
    }
}
