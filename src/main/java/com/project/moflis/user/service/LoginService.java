package com.project.moflis.user.service;

import com.project.moflis.token.dto.response.TokenResponse;
import com.project.moflis.token.service.RefreshTokenService;
import com.project.moflis.user.command.LoginUserCommand;
import com.project.moflis.user.dto.response.LoginUserResponse;
import com.project.moflis.user.entity.User;
import com.project.moflis.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final TokenService tokenService;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.tokenService = tokenService;
    }

    public LoginUserResponse loginUser(LoginUserCommand command) {
        User user = userRepository.findByEmail(command.getEmail());
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 이메일입니다.");
        }


        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치 하지 않습니다.");
        }

        TokenResponse response = refreshTokenService.generateAndStoreTokens(user);

        return LoginUserResponse.from(user, response.getAccessToken());

    }

    public void logout(String refreshToken) {
        Long userId = tokenService.parseUserId(refreshToken);
        refreshTokenService.deleteRefreshToken(userId);
    }
}
