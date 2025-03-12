package com.coursemaster.mapper;

import com.coursemaster.dto.user.UserRequestDto;
import com.coursemaster.dto.user.UserResponseDto;
import com.coursemaster.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserRequestDto, UserResponseDto> {

    @Override
    @Mapping(target = "password", ignore = true)
    User toEntity(UserRequestDto request);
}
