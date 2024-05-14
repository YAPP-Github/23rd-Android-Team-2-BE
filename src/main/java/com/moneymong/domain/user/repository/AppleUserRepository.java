package com.moneymong.domain.user.repository;

import com.moneymong.domain.user.entity.AppleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppleUserRepository extends JpaRepository<AppleUser, Long> {
}
