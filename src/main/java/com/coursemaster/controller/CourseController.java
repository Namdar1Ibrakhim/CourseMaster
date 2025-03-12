package com.coursemaster.controller;

import com.coursemaster.dto.course.CourseRequestDto;
import com.coursemaster.dto.course.CourseResponseDto;
import com.coursemaster.dto.course.CourseUpdateRequestDto;
import com.coursemaster.entity.User;
import com.coursemaster.enums.Role;
import com.coursemaster.service.CourseService;
import com.sun.security.auth.UserPrincipal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course")
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CourseResponseDto> getAllCourses(Pageable pageable) {
        return courseService.getAll(pageable);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponseDto getCourseById(
            @PathVariable @Positive(message = "Id must be positive") long id
    ) {
        return courseService.getById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponseDto createCourse(
            @RequestBody @Valid CourseRequestDto courseRequestDto
    ) {
        return courseService.create(courseRequestDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponseDto updateCourse(
            @PathVariable @Positive(message = "Id must be positive") long id,
            @RequestBody @Valid CourseUpdateRequestDto courseRequestDto
    ) {
        return courseService.update(id, courseRequestDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(
            @PathVariable @Positive(message = "Id must be positive") long id
    ) {
        courseService.delete(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/{courseId}/register/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponseDto registerStudent(
            @PathVariable @Positive(message = "Course ID must be positive") long courseId,
            @PathVariable @Positive(message = "Student ID must be positive") long studentId,
            @AuthenticationPrincipal User user
    ) {
        if (user.getRole()== Role.USER && user.getId() != studentId) {
            throw new AccessDeniedException("Students can only register themselves.");
        }
        return courseService.registerStudent(courseId, studentId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{courseId}/unregister/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponseDto removeStudent(
            @PathVariable @Positive(message = "Course ID must be positive") long courseId,
            @PathVariable @Positive(message = "Student ID must be positive") long studentId) {
        return courseService.removeStudent(courseId, studentId);
    }
}
