package com.coursemaster.controller;

import com.coursemaster.dto.user.UserRequestDto;
import com.coursemaster.dto.user.UserResponseDto;
import com.coursemaster.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserResponseDto> getAllUsers(Pageable pageable) {
        return userService.getAll(pageable);
    }

    @PreAuthorize("hasAuthority('ADMIN') or #id == principal.id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUserById(
            @PathVariable @Positive(message = "Id must be positive") long id
    ) {
        return userService.getById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(
            @RequestBody @Valid UserRequestDto userRequestDto
    ) {
        return userService.create(userRequestDto);
    }

    @PreAuthorize("hasAuthority('ADMIN') or #id == principal.id")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto updateUser(
            @PathVariable @Positive(message = "Id must be positive") long id,
            @RequestBody @Valid UserRequestDto userRequestDto
    ) {
        return userService.update(id, userRequestDto);
    }

    @PreAuthorize("hasAuthority('ADMIN') or #id == principal.id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
            @PathVariable @Positive(message = "Id must be positive") long id
    ) {
        userService.delete(id);
    }
}
