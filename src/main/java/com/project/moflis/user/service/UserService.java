package com.project.moflis.user.service;

import com.project.moflis.user.dto.UserDTO;
import com.project.moflis.user.entity.User;
import com.project.moflis.user.mapper.UserMapper;
import com.project.moflis.user.repository.UserRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void updateEmailVerified(String email) {
        User user = userRepository.findByEmail(email);
        user.isVerified();
        userRepository.save(user);

    }
}
