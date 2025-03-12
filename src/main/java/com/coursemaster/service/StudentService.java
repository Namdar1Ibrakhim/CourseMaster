package com.coursemaster.service;

import com.coursemaster.dto.student.StudentRequestDto;
import com.coursemaster.dto.student.StudentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {

    Page<StudentResponseDto> getAll(Pageable pageable);
    StudentResponseDto getById(long id);
    StudentResponseDto create(StudentRequestDto requestDto);
    StudentResponseDto update(long id, StudentRequestDto requestDto);
    void delete(long id);
}
