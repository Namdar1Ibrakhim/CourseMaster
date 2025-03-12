package com.coursemaster.mapper;

import com.coursemaster.dto.student.StudentRequestDto;
import com.coursemaster.dto.student.StudentResponseDto;
import com.coursemaster.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface StudentMapper extends Mappable<Student, StudentRequestDto, StudentResponseDto> {

    @Override
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "registrationDate", expression = "java(entity.getCreatedDate())")
    StudentResponseDto toDto(Student entity);
}
