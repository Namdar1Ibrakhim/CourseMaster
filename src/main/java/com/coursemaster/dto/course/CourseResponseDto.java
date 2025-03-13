package com.coursemaster.dto.course;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record CourseResponseDto(
        long id,
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
){ }