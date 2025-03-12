package com.coursemaster.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDto (

        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        @NotBlank(message = "Role cannot be blank")
        @Pattern(regexp = "^(USER|ADMIN)$", message = "Role can be USER or ADMIN")
        String role

) { }
