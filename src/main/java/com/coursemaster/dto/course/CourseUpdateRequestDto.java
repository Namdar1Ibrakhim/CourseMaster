package com.coursemaster.dto.course;

import java.time.LocalDateTime;

public record CourseUpdateRequestDto(
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
) { }
