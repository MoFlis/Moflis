package com.project.moflis.user.service;

import com.project.moflis.global.security.jwt.JwtProvider;
import com.project.moflis.user.command.JoinUserCommand;
import com.project.moflis.user.command.LoginUserCommand;
import com.project.moflis.user.dto.response.JoinUserResponse;
import com.project.moflis.user.dto.response.LoginUserResponse;
import com.project.moflis.user.entity.User;
import com.project.moflis.user.enums.UserStatus;
import com.project.moflis.user.mapper.UserMapper;
import com.project.moflis.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public JoinUserResponse joinUser(JoinUserCommand command) {
        String encodedPassword = passwordEncoder.encode(command.getPassword());
        User user = User.builder()
                .email(command.getEmail())
                .name(command.getName())
                .password(encodedPassword)
                .phone(command.getPhone())
                .birth(command.getBirth())
                .address(command.getAddress())
                .nickname(command.getNickname())
                .gender(command.isGender())
                .kakao(command.getKakao())
                .joinDate(LocalDateTime.now())
                .userStatus(UserStatus.ACTIVE)
                .grade(command.getGrade())
                .build();
        return UserMapper.INSTANCE.toJoinUserCommand(userRepository.save(user));
    }


    public LoginUserResponse loginUser(LoginUserCommand command) {
        User user = userRepository.findByEmail(command.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치 하지 않습니다.");
        }

        String accessToken = jwtProvider.getAccessToken(user.getId(), user.getName(), user.getGrade());
        String refreshToken = jwtProvider.getRefreshToken(user.getId(), user.getName(), user.getGrade());
        
        user.updateRefreshToken(refreshToken);
        userRepository.save(user);

        return LoginUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();

    }

    public User getByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("userId를 찾을 수 없습니다"));
    }

    @Transactional
    public void updateRefreshToken(User user) {
        userRepository.save(user);
    }

    public String findUserEmail(String name, String phone) {
        return userRepository.findByNameAndPhone(name, phone)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."))
                .getEmail();
    }
}
