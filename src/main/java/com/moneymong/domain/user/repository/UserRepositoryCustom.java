package com.moneymong.domain.user.repository;

import com.moneymong.domain.user.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findByUserIdByProviderAndOauthId(String provider, String oauthId);
}
