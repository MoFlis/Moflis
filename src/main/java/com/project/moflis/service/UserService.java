package com.project.moflis.service;

import com.project.moflis.dto.UserDTO;
import com.project.moflis.mapper.UserMapper;
import com.project.moflis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO getUserAddress(Integer userId) {
        return UserMapper.INSTANCE.toUserDto(userRepository.findById(userId).get());
    }
}
