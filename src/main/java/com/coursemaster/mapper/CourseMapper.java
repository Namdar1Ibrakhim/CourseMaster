package com.coursemaster.mapper;

import com.coursemaster.dto.course.CourseRequestDto;
import com.coursemaster.dto.course.CourseResponseDto;
import com.coursemaster.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = StudentMapper.class)
public interface CourseMapper extends Mappable<Course, CourseRequestDto, CourseResponseDto>{

}
