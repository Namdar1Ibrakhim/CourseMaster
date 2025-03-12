package com.coursemaster.service;


import com.coursemaster.dto.user.UserRequestDto;
import com.coursemaster.dto.user.UserResponseDto;
import com.coursemaster.entity.Student;
import com.coursemaster.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserResponseDto> getAll(Pageable pageable);
    UserResponseDto getById(long id);
    UserResponseDto create(UserRequestDto requestDto);
    UserResponseDto update(long id, UserRequestDto requestDto);
    void delete(long id);
    User getEntityById(long id);
    void throwExceptionIfUserExists(String username);
}
