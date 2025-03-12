package com.coursemaster.dto.student;

import com.coursemaster.dto.user.UserResponseDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record StudentResponseDto(
        long id,
        String firstName,
        String lastName,
        String email,
        LocalDateTime registrationDate,
        UserResponseDto user
) { }
