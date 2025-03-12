package com.coursemaster.dto.course;

import com.coursemaster.dto.student.StudentResponseDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CourseResponseDto(
        long id,
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        List<StudentResponseDto> students
){ }