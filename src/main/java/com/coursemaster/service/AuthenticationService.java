package com.coursemaster.service;


import com.coursemaster.dto.auth.AuthenticationRequestDto;
import com.coursemaster.dto.auth.AuthenticationResponseDto;
import com.coursemaster.dto.auth.RegisterRequestDto;

public interface AuthenticationService {
    AuthenticationResponseDto register(RegisterRequestDto request);
    AuthenticationResponseDto authenticate(AuthenticationRequestDto request);
    AuthenticationResponseDto refreshToken(String authHeader);
}
