package com.project.moflis.user.service;

import com.project.moflis.global.security.jwt.JwtProvider;
import com.project.moflis.global.security.jwt.TokenClaims;
import com.project.moflis.token.dto.response.TokenResponse;
import com.project.moflis.token.service.RefreshTokenService;
import com.project.moflis.user.command.JoinUserCommand;
import com.project.moflis.user.command.LoginUserCommand;
import com.project.moflis.user.dto.response.FindIdResponse;
import com.project.moflis.user.dto.response.JoinUserResponse;
import com.project.moflis.user.dto.response.LoginUserResponse;
import com.project.moflis.user.entity.User;
import com.project.moflis.user.mapper.UserMapper;
import com.project.moflis.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtProvider jwtProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public JoinUserResponse joinUser(JoinUserCommand command) {
        String encodedPassword = passwordEncoder.encode(command.getPassword());
        User user = User.toEntity(command, encodedPassword);
        return UserMapper.INSTANCE.toJoinUserCommand(userRepository.save(user));
    }


    public LoginUserResponse loginUser(LoginUserCommand command) {
        User user = userRepository.findByEmail(command.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치 하지 않습니다.");
        }

        TokenResponse response = refreshTokenService.generateAndStoreTokens(user);

        return LoginUserResponse.from(user, response.getAccessToken());

    }

    public User getByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("userId를 찾을 수 없습니다"));
    }

    public FindIdResponse findUserEmail(String name, String phone) {
        User user = userRepository.findByNameAndPhone(name, phone);
        if (user == null) {
            throw new IllegalArgumentException("해당 이름과 전화번호로 등록된 사용자가 없습니다.");
        }

        return new FindIdResponse(user.getEmail());
    }

    public void logout(String refreshToken) {

        if (!jwtProvider.verify(refreshToken)) {
            throw new RuntimeException("유효하지 않은 토큰입니다");
        }

        TokenClaims claims = jwtProvider.getClaims(refreshToken);
        Long userId = claims.getUserId();
        refreshTokenService.deleteRefreshToken(userId);
    }
}
