package com.coursemaster.service;

import com.coursemaster.dto.course.CourseResponseDto;
import com.coursemaster.dto.student.StudentRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    Page<CourseResponseDto> getAll(Pageable pageable);
    CourseResponseDto getById(long id);
    CourseResponseDto create(StudentRequestDto requestDto);
    CourseResponseDto update(long id, StudentRequestDto requestDto);
    void delete(long id);
}
