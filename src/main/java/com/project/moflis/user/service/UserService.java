package com.project.moflis.user.service;

import com.project.moflis.global.security.jwt.JwtProvider;
import com.project.moflis.token.dto.response.TokenResponse;
import com.project.moflis.token.service.TokenStoreService;
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
    private final JwtProvider jwtProvider;
    private final TokenStoreService tokenStoreService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, TokenStoreService tokenStoreService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.tokenStoreService = tokenStoreService;
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

        TokenResponse response = tokenStoreService.generateAndStoreTokens(user);

        return LoginUserResponse.from(user, response.getAccessToken(), response.getRefreshToken());

    }

    public User getByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("userId를 찾을 수 없습니다"));
    }

    @Transactional
    public void updateRefreshToken(User user) {
        userRepository.save(user);
    }

    public FindIdResponse findUserEmail(String name, String phone) {
        User user = userRepository.findByNameAndPhone(name, phone)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return new FindIdResponse(user.getEmail());
    }
}
