package com.coursemaster.service;

import com.coursemaster.dto.student.StudentRequestDto;
import com.coursemaster.dto.student.StudentResponseDto;
import com.coursemaster.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {

    Page<StudentResponseDto> getAll(Pageable pageable);
    StudentResponseDto getById(long id);
    StudentResponseDto create(StudentRequestDto requestDto);
    StudentResponseDto update(long id, StudentRequestDto requestDto);
    void delete(long id);
    Student getEntityById(long id);
    Page<StudentResponseDto> searchStudents(String searchName, Pageable pageable);
}
