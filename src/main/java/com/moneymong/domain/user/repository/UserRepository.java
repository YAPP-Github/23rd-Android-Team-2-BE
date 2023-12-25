package com.moneymong.domain.user.repository;

import com.moneymong.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	//TODO QueryDSL로 refactor
    @Query("""
		SELECT u
		FROM User u
		WHERE u.provider = :provider
		AND u.oauthId = :oauthId
		""")
    Optional<User> findByUserIdByProviderAndOauthId(
            @Param("provider") String provider,
            @Param("oauthId") String oauthId);

    Optional<User> findByUserToken(String userToken);
}
