package com.coursemaster.controller;

import com.coursemaster.dto.course.CourseResponseDto;
import com.coursemaster.dto.student.StudentRequestDto;
import com.coursemaster.dto.student.StudentResponseDto;
import com.coursemaster.entity.Course;
import com.coursemaster.service.CourseService;
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
@RequestMapping("/api/v1/course")
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CourseResponseDto> getAllStudents(Pageable pageable) {
        return courseService.getAll(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponseDto getStudentById(
            @PathVariable @Positive(message = "Id must be positive") long id
    ) {
        return courseService.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponseDto createStudent(
            @RequestBody @Valid StudentRequestDto studentRequestDto
    ) {
        return courseService.create(studentRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponseDto updateStudent(
            @PathVariable @Positive(message = "Id must be positive") long id,
            @RequestBody @Valid StudentRequestDto studentRequestDto
    ) {
        return courseService.update(id, studentRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(
            @PathVariable @Positive(message = "Id must be positive") long id
    ) {
        courseService.delete(id);
    }
}
