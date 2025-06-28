package com.project.moflis.user.service;

import com.project.moflis.user.command.JoinUserCommand;
import com.project.moflis.user.dto.response.FindIdResponse;
import com.project.moflis.user.dto.response.JoinUserResponse;
import com.project.moflis.user.entity.User;
import com.project.moflis.user.factory.UserFactory;
import com.project.moflis.user.mapper.UserMapper;
import com.project.moflis.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public JoinUserResponse joinUser(JoinUserCommand command) {
        String encodedPassword = passwordEncoder.encode(command.getPassword());
        User user = UserFactory.createUser(command, encodedPassword);
        return UserMapper.INSTANCE.toJoinUserCommand(userRepository.save(user));
    }

    public User getByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 정보를 처리할 수 없습니다"));
    }

    public FindIdResponse findUserEmail(String name, String phone) {
        User user = userRepository.findByNameAndPhone(name, phone);
        if (user == null) {
            throw new IllegalArgumentException("입력하신 정보로 가입된 계정을 찾을 수 없습니다");
        }

        return new FindIdResponse(user.getEmail());
    }

}
