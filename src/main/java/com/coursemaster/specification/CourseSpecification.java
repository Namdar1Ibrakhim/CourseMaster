package com.coursemaster.specification;

import com.coursemaster.entity.Course;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class CourseSpecification {

    public static Specification<Course> hasName(String name) {
        return (root, query, criteriaBuilder) -> 
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Course> hasStartDate(LocalDateTime startDate) {
        return (root, query, criteriaBuilder) -> 
                startDate == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate);
    }

    public static Specification<Course> hasEndDate(LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> 
                endDate == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDate);
    }
}
