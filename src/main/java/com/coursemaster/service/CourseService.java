package com.coursemaster.service;

import com.coursemaster.dto.course.CourseRequestDto;
import com.coursemaster.dto.course.CourseResponseDto;
import com.coursemaster.dto.course.CourseUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface CourseService {
    Page<CourseResponseDto> getAll(String name, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    CourseResponseDto getById(long id);
    CourseResponseDto create(CourseRequestDto requestDto);
    CourseResponseDto update(long id, CourseUpdateRequestDto requestDto);
    void delete(long id);
    CourseResponseDto registerStudent(long courseId, long studentId);
    CourseResponseDto removeStudent(long courseId, long studentId);
}
