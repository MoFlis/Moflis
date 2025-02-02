package com.project.moflis.mapper;

import com.project.moflis.entity.User;

import javax.annotation.processing.Generated;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2025-01-03T17:16:07+0900",
        comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toUserDto(User users) {
        if (users == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId(users.getId());
        userDTO.setName(users.getName());
        userDTO.setEmail(users.getEmail());
        userDTO.setPassword(users.getPassword());
        userDTO.setPhone(users.getPhone());
        userDTO.setBirth(users.getBirth());
        userDTO.setAddress(users.getAddress());
        userDTO.setNickname(users.getNickname());
        userDTO.setGender(users.isGender());
        userDTO.setKakao(users.getKakao());
        userDTO.setJoinDate(users.getJoinDate());
        userDTO.setUserStatus(users.getUserStatus());
        userDTO.setGrade(users.getGrade());

        return userDTO;
    }

    @Override
    public User toUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhone(userDTO.getPhone());
        user.setBirth(userDTO.getBirth());
        user.setAddress(userDTO.getAddress());
        user.setNickname(userDTO.getNickname());
        user.setGender(userDTO.isGender());
        user.setKakao(userDTO.getKakao());
        user.setJoinDate(userDTO.getJoinDate());
        user.setUserStatus(userDTO.getUserStatus());
        user.setGrade(userDTO.getGrade());

        return user;
    }
}
