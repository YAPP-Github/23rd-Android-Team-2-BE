package com.moneymong.global.security.service;

import com.moneymong.domain.user.api.request.LoginRequest;
import com.moneymong.domain.user.api.request.UserDeleteRequest;
import com.moneymong.domain.user.entity.User;
import com.moneymong.domain.user.repository.UserRepository;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import com.moneymong.global.security.oauth.dto.OAuthUserDataRequest;
import com.moneymong.global.security.oauth.dto.OAuthUserDataResponse;
import com.moneymong.global.security.oauth.handler.OAuthAuthenticationHandler;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.moneymong.global.exception.enums.ErrorCode.USER_NOT_FOUND;

@Service
public class OAuthService {

    private final Map<OAuthProvider, OAuthAuthenticationHandler> oAuthAuthenticationHandlers;
    private final UserRepository userRepository;

    public OAuthService(List<OAuthAuthenticationHandler> oAuthAuthenticationHandlers, UserRepository userRepository) {
        this.oAuthAuthenticationHandlers = oAuthAuthenticationHandlers.stream().collect(
                Collectors.toConcurrentMap(OAuthAuthenticationHandler::getAuthProvider, Function.identity())
        );
        this.userRepository = userRepository;
    }

    public OAuthUserDataResponse login(LoginRequest loginRequest) {
        OAuthProvider oAuthProvider = OAuthProvider.get(loginRequest.getProvider());

        OAuthAuthenticationHandler oAuthHandler = this.oAuthAuthenticationHandlers.get(oAuthProvider);

        OAuthUserDataRequest request = new OAuthUserDataRequest(
                loginRequest.getAccessToken(),
                loginRequest.getCode(),
                loginRequest.getName()
        );

        return oAuthHandler.getOAuthUserData(request);
    }

    public void revoke(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        OAuthProvider oAuthProvider = OAuthProvider.get(user.getProvider());
        OAuthAuthenticationHandler oAuthHandler = this.oAuthAuthenticationHandlers.get(oAuthProvider);

        oAuthHandler.unlink(userId);
    }
}
