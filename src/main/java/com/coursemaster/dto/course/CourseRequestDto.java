package com.coursemaster.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CourseRequestDto(
        @NotNull @NotBlank
        String name,

        @NotNull @NotBlank
        String description,

        @NotNull
        LocalDateTime startDate,

        @NotNull
        LocalDateTime endDate
) { }
