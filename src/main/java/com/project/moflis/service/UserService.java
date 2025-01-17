package com.project.moflis.service;

import com.project.moflis.dto.user.UserDTO;
import com.project.moflis.mapper.UserMapper;
import com.project.moflis.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserAddress(Integer userId) {
        return UserMapper.INSTANCE.toUserDto(userRepository.findById(userId).get());
    }
}
