package com.coursemaster.service.impl;

import com.coursemaster.dto.course.CourseRequestDto;
import com.coursemaster.dto.course.CourseResponseDto;
import com.coursemaster.dto.course.CourseUpdateRequestDto;
import com.coursemaster.dto.mail.MailStructureDto;
import com.coursemaster.entity.Course;
import com.coursemaster.entity.Student;
import com.coursemaster.exceptions.entity.EntityAlreadyExistsException;
import com.coursemaster.exceptions.entity.EntityNotFoundException;
import com.coursemaster.mapper.CourseMapper;
import com.coursemaster.rabbit.RabbitMailProducer;
import com.coursemaster.repository.CourseRepository;
import com.coursemaster.service.CourseService;
import com.coursemaster.service.StudentService;
import com.coursemaster.specification.CourseSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final StudentService studentService;
    private final RabbitMailProducer rabbitMailProducer;

    private static final String MAIL_SUBJECT =
            "Уведомление о вашем курсе!";

    private static final String MAIL_TEMPLATE =
            "Вы успешно зарегистрировались на курс {0}. Описание курса: {1}. Дата начала: {2}.";

    @Override
    public Page<CourseResponseDto>  getAll(String name, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Specification<Course> specification = Specification
                .where(CourseSpecification.hasName(name))
                .and(CourseSpecification.hasStartDate(startDate))
                .and(CourseSpecification.hasEndDate(endDate));

        Page<Course> coursePage = courseRepository.findAll(specification, pageable);
        return coursePage.map(courseMapper::toDto);
    }

    @Override
    public CourseResponseDto getById(long id) {
        Course course = getEntityById(id);
        return courseMapper.toDto(course);
    }

    @Override
    public CourseResponseDto create(CourseRequestDto courseRequestDto) {
        throwExceptionIfCourseExists(courseRequestDto.name());

        Course course = courseMapper.toEntity(courseRequestDto);
        course = courseRepository.save(course);

        return courseMapper.toDto(course);
    }

    @Override
    public CourseResponseDto update(long id, CourseUpdateRequestDto dto) {
        Course course = getEntityById(id);

        if (dto.name() != null) {
            course.setName(dto.name());
        }
        if (dto.description() != null) {
            course.setDescription(dto.description());
        }
        if (dto.startDate() != null) {
            course.setStartDate(dto.startDate());
        }
        if (dto.endDate() != null) {
            course.setEndDate(dto.endDate());
        }

        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Override
    public CourseResponseDto registerStudent(long courseId, long studentId) {
        Student student = studentService.getEntityById(studentId);
        Course course = getEntityById(courseId);

        boolean alreadyRegistered = course.getStudents().stream()
                .anyMatch(s -> s.getEmail().equalsIgnoreCase(student.getEmail()));

        if (alreadyRegistered) {
            throw new EntityAlreadyExistsException("Student with email " + student.getEmail() + " is already registered in course with ID " + courseId);
        }

        course.getStudents().add(student);
        course = courseRepository.save(course);

        String formattedMessage = MessageFormat.format(MAIL_TEMPLATE,
                course.getName(),
                course.getDescription(),
                course.getStartDate().toString());

        rabbitMailProducer.sendMailMessage(new MailStructureDto(student.getEmail(), MAIL_SUBJECT, formattedMessage));
        return courseMapper.toDto(course);
    }

    @Override
    public CourseResponseDto removeStudent(long courseId, long studentId) {
        Student student = studentService.getEntityById(studentId);
        Course course = getEntityById(courseId);

        boolean removed = course.getStudents().removeIf(s -> s.getEmail().equalsIgnoreCase(student.getEmail()));

        if (!removed) {
            throw new EntityNotFoundException("Student with email " + student.getEmail() + " not found in course with ID " + courseId);
        }

        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Override
    public void delete(long id) {
        Course course = getEntityById(id);
        courseRepository.delete(course);
    }

    private Course getEntityById(long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course with id " + id + " does not exist"));
    }

    public void throwExceptionIfCourseExists(String name) {
        courseRepository.findByName(name)
                .ifPresent(foundCourse -> {
                    throw new EntityAlreadyExistsException(
                            "Course with the name " + foundCourse + " already exists"
                    );
                });
    }
}
