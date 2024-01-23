package com.moneymong.domain.user.repository;

import com.moneymong.domain.user.entity.UserUniversity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserUniversityRepository extends JpaRepository<UserUniversity, Long> {
    boolean existsByUserId(Long userId);

    Optional<UserUniversity> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
