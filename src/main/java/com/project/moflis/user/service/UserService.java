package com.project.moflis.user.service;

import com.project.moflis.user.dto.UserDTO;
import com.project.moflis.user.entity.User;
import com.project.moflis.user.mapper.UserMapper;
import com.project.moflis.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));
    }

    public UserDTO getUserAddress(Long userId) {
        return UserMapper.INSTANCE.toUserDto(userRepository.findById(userId).get());
    }

    public void logout(String refreshToken) {

        if (!jwtProvider.verify(refreshToken)) {
            throw new RuntimeException("유효하지 않은 토큰입니다");
        }

        TokenClaims claims = jwtProvider.getClaims(refreshToken);
        Long userId = claims.getUserId();
        refreshTokenService.deleteRefreshToken(userId);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저 정보가 존재하지 않습니다. id=" + userId));
    }
}
