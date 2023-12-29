package com.moneymong.domain.user.service;

import com.moneymong.domain.user.api.request.CreateUserUniversityRequest;
import com.moneymong.domain.user.api.request.UpdateUserUniversityRequest;
import com.moneymong.domain.user.entity.UserUniversity;
import com.moneymong.domain.user.repository.UserUniversityRepository;
import com.moneymong.global.exception.custom.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.moneymong.global.exception.enums.ErrorCode.USER_UNIVERSITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserUniversityService {
    private final UserUniversityRepository userUniversityRepository;

    @Transactional(readOnly = true)
    public boolean exists(Long userId) {
        return userUniversityRepository.existsByUserId(userId);
    }

    @Transactional
    public void update(Long userId, UpdateUserUniversityRequest request) {
        UserUniversity userUniversity = userUniversityRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(USER_UNIVERSITY_NOT_FOUND));

        userUniversity.update(request.getUniversityName(), request.getGrade());
    }

    @Transactional
    public void create(Long userId, CreateUserUniversityRequest request) {
        UserUniversity userUniversity = UserUniversity.of(userId, request.getUniversityName(), request.getGrade());
        userUniversityRepository.save(userUniversity);
    }
}
