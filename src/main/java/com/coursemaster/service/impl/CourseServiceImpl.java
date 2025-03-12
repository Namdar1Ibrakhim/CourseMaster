package com.coursemaster.service.impl;

import com.coursemaster.dto.course.CourseResponseDto;
import com.coursemaster.dto.student.StudentRequestDto;
import com.coursemaster.entity.Course;
import com.coursemaster.entity.Student;
import com.coursemaster.mapper.CourseMapper;
import com.coursemaster.repository.CourseRepository;
import com.coursemaster.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public Page<CourseResponseDto> getAll(Pageable pageable) {
        log.info("Retrieving Students, page number: {}, page size : {}", pageable.getPageNumber(), pageable.getPageSize());

        Page<Course> coursePage = courseRepository.findAll(pageable);

        log.info("Finished retrieving Students, page number: {}, page size : {}", pageable.getPageNumber(), pageable.getPageSize());

        return coursePage.map(courseMapper::toDto);
    }

    @Override
    public CourseResponseDto getById(long id) {
        return null;
    }

    @Override
    public CourseResponseDto create(StudentRequestDto requestDto) {
        return null;
    }

    @Override
    public CourseResponseDto update(long id, StudentRequestDto requestDto) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
