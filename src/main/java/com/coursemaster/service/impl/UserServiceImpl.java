package com.coursemaster.service.impl;

import com.coursemaster.dto.user.UserRequestDto;
import com.coursemaster.dto.user.UserResponseDto;
import com.coursemaster.entity.User;
import com.coursemaster.enums.Role;
import com.coursemaster.exceptions.entity.EntityAlreadyExistsException;
import com.coursemaster.exceptions.entity.EntityNotFoundException;
import com.coursemaster.mapper.UserMapper;
import com.coursemaster.repository.UserRepository;
import com.coursemaster.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserResponseDto> getAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(userMapper::toDto);
    }

    @Override
    public UserResponseDto getById(long id) {
        User user = getEntityById(id);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto create(UserRequestDto requestDto) {
        throwExceptionIfUserExists(requestDto.username());

        User user = userMapper.toEntity(requestDto);
        user.setPassword(
                passwordEncoder.encode(requestDto.password())
        );

        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto update(long id, UserRequestDto requestDto) {
        User user = getEntityById(id);

        String oldUsername = user.getUsername();
        String newUsername = requestDto.username();

        if (!Objects.equals(oldUsername, newUsername)) {
            throwExceptionIfUserExists(newUsername);
            user.setUsername(newUsername);
        }

        user.setRole(
                Role.valueOf(requestDto.role())
        );
        user.setPassword(
                passwordEncoder.encode(requestDto.password())
        );

        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        User user = getEntityById(id);
        userRepository.delete(user);
    }

    @Override
    public User getEntityById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " does not exist"));
    }

    @Override
    public void throwExceptionIfUserExists(String username) {
        userRepository.findByUsername(username)
                .ifPresent(foundUser -> {
                    throw new EntityAlreadyExistsException(
                            "User with the username " + foundUser.getUsername() + " already exists"
                    );
                });
    }

}
