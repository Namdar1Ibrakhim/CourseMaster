package com.coursemaster.service.impl;

import com.coursemaster.dto.auth.AuthenticationRequestDto;
import com.coursemaster.dto.auth.AuthenticationResponseDto;
import com.coursemaster.dto.auth.RegisterRequestDto;
import com.coursemaster.entity.Student;
import com.coursemaster.entity.User;
import com.coursemaster.enums.Role;
import com.coursemaster.exceptions.auth.JwtSubjectMissingException;
import com.coursemaster.exceptions.auth.JwtTokenExpiredException;
import com.coursemaster.exceptions.entity.EntityNotFoundException;
import com.coursemaster.jwt.JwtFactory;
import com.coursemaster.jwt.JwtParser;
import com.coursemaster.jwt.JwtValidator;
import com.coursemaster.repository.UserRepository;
import com.coursemaster.service.AuthenticationService;
import com.coursemaster.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtFactory jwtFactory;
    private final JwtValidator jwtValidator;
    private final JwtParser jwtParser;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthenticationResponseDto register(RegisterRequestDto request) {
        userService.throwExceptionIfUserExists(request.username());

        Student student = Student.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .student(student)
                .build();

        student.setUser(user);
        userRepository.save(user);

        log.info("User saved to the database with username: {}", user.getUsername());

        String accessToken = jwtFactory.generateAccessToken(user);
        String refreshToken = jwtFactory.generateRefreshToken(user);

        return AuthenticationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with username " + request.username() + " not found"
                ));

        String accessToken = jwtFactory.generateAccessToken(user);
        String refreshToken = jwtFactory.generateRefreshToken(user);

        return AuthenticationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponseDto refreshToken(String authHeader) {
        String refreshToken = authHeader.substring(7);

        User user = jwtParser.extractUsername(refreshToken)
                .map(userRepository::findByUsername)
                .orElseThrow(() -> new JwtSubjectMissingException("JWT subject cannot be null"))
                .orElseThrow(() -> new EntityNotFoundException("User with this username was not found"));

        jwtValidator.ifTokenExpiredThrow(refreshToken, () -> new JwtTokenExpiredException("Refresh token has expired"));

        String accessToken = jwtFactory.generateAccessToken(user);

        return AuthenticationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
