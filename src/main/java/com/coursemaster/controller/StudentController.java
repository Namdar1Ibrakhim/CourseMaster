package com.coursemaster.controller;

import com.coursemaster.dto.student.StudentRequestDto;
import com.coursemaster.dto.student.StudentResponseDto;
import com.coursemaster.service.StudentService;
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
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<StudentResponseDto> getAllStudents(Pageable pageable) {
        return studentService.getAll(pageable);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponseDto getStudentById(
            @PathVariable @Positive(message = "Id must be positive") long id
    ) {
        return studentService.getById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponseDto createStudent(
            @RequestBody @Valid StudentRequestDto studentRequestDto
    ) {
        return studentService.create(studentRequestDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponseDto updateStudent(
            @PathVariable @Positive(message = "Id must be positive") long id,
            @RequestBody @Valid StudentRequestDto studentRequestDto
    ) {
        return studentService.update(id, studentRequestDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(
            @PathVariable @Positive(message = "Id must be positive") long id
    ) {
        studentService.delete(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/search")
    public Page<StudentResponseDto> searchStudents(
            @RequestParam("searchName") String searchName,
            Pageable pageable
    ) {
        return studentService.searchStudents(searchName, pageable);
    }

}
