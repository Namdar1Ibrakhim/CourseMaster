package com.coursemaster.dto.user;

import com.coursemaster.enums.Role;
import lombok.Builder;

@Builder
public record UserResponseDto(
        long id,
        String username,
        Role role
) { }
