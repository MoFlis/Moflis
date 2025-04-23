package com.project.moflis.user.mapper;

import com.project.moflis.user.command.JoinUserCommand;
import com.project.moflis.user.dto.UserDTO;
import com.project.moflis.user.dto.response.JoinUserResponse;
import com.project.moflis.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDto(User users);

    User toUser(JoinUserCommand joinUserCommand);


    JoinUserResponse toJoinUserCommand(User user);

    List<UserDTO> toUserDtoList(List<User> userList);

    List<User> toUserList(List<UserDTO> userDtoList);

}
