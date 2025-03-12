package com.coursemaster.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDto(

        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "Password cannot be blank")
        String password

) { }
