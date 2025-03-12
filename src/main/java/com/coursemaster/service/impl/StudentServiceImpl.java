package com.coursemaster.service.impl;

import com.coursemaster.dto.student.StudentRequestDto;
import com.coursemaster.dto.student.StudentResponseDto;
import com.coursemaster.entity.Student;
import com.coursemaster.entity.User;
import com.coursemaster.exceptions.entity.EntityAlreadyExistsException;
import com.coursemaster.exceptions.entity.EntityNotFoundException;
import com.coursemaster.mapper.StudentMapper;
import com.coursemaster.repository.StudentRepository;
import com.coursemaster.service.StudentService;
import com.coursemaster.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;
    private final StudentMapper studentMapper;


    @Override
    public Page<StudentResponseDto> getAll(Pageable pageable) {
        log.info("Retrieving Students, page number: {}, page size : {}", pageable.getPageNumber(), pageable.getPageSize());

        Page<Student> studentPage = studentRepository.findAll(pageable);

        log.info("Finished retrieving Students, page number: {}, page size : {}", pageable.getPageNumber(), pageable.getPageSize());

        return studentPage.map(studentMapper::toDto);
    }

    @Override
    public StudentResponseDto getById(long id) {
        log.info("Retrieving Student with ID: {}", id);

        Student student = getEntityById(id);
        StudentResponseDto responseDto = studentMapper.toDto(student);

        log.info("Finished retrieving Student by ID: {}", student.getId());

        return responseDto;
    }

    @Override
    @Transactional
    public StudentResponseDto create(StudentRequestDto requestDto) {
        log.info("Creating new Student for User with ID {}", requestDto.userId());

        User user = userService.getEntityById(requestDto.userId());

        throwExceptionIfUserAlreadyLinkedToStudent(user);

        Student student = studentMapper.toEntity(requestDto);
        student.setUser(user);

        log.info("User with ID {} are set for Student", user.getId());

        student = studentRepository.save(student);
        StudentResponseDto responseDto = studentMapper.toDto(student);

        log.info("Created new Student with ID: {}", student.getId());

        return responseDto;
    }

    @Override
    @Transactional
    public StudentResponseDto update(long id, StudentRequestDto requestDto) {
        log.info("Updating Student with ID: {}", id);

        Student student = getEntityById(id);

        User user = student.getUser();

        long oldUserId = user.getId();

        long newUserId = requestDto.userId();

        if (oldUserId != newUserId) {
            user = userService.getEntityById(newUserId);
            throwExceptionIfUserAlreadyLinkedToStudent(user);
            student.setUser(user);

            log.info("User with ID {} has been set for Student with ID: {}", user.getId(), student.getId());
        }

        student.setFirstName(requestDto.firstName());
        student.setLastName(requestDto.lastName());

        student = studentRepository.save(student);
        StudentResponseDto responseDto = studentMapper.toDto(student);

        log.info("Updated Student with ID: {}", student.getId());

        return responseDto;
    }

    @Override
    @Transactional
    public void delete(long id) {
        log.info("Deleting Student with ID: {}", id);

        Student student = getEntityById(id);
        studentRepository.delete(student);

        log.info("Deleted Student with ID: {}", student.getId());
    }

    public Student getEntityById(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id " + id + " does not exist"));
    }

    private void throwExceptionIfUserAlreadyLinkedToStudent(User user) {
        Student student = user.getStudent();

        if (student != null) {
            throw new EntityAlreadyExistsException(
                    "User already linked to student with ID : " + student.getId()
            );
        }
    }
}
