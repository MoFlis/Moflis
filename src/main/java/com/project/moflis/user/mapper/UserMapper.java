package com.project.moflis.user.mapper;

import com.project.moflis.user.dto.UserDTO;
import com.project.moflis.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDto(User users);

    User toUser(UserDTO userDTO);

    List<UserDTO> toUserDtoList(List<User> userList);

    List<User> toUserList(List<UserDTO> userDtoList);

}
