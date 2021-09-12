package com.betulsahin.schoolmanagementsystemv5.mappers;

import com.betulsahin.schoolmanagementsystemv5.dtos.CourseRegistrationDto;
import com.betulsahin.schoolmanagementsystemv5.entities.CourseRegistration;
import com.betulsahin.schoolmanagementsystemv5.services.CourseService;
import com.betulsahin.schoolmanagementsystemv5.services.StudentService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CourseRegistrationMapper {
    @Autowired
    protected StudentService studentService;
    @Autowired
    protected CourseService courseService;

    @Mapping(target = "student", expression = "java(studentService.findById(courseRegistrationDto.getStudentId()))")
    @Mapping(target = "course", expression = "java(courseService.findById(courseRegistrationDto.getCourseId()))")
    public abstract CourseRegistration map(CourseRegistrationDto courseRegistrationDto);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "courseId", source = "course.id")
    public abstract CourseRegistrationDto mapToDto(CourseRegistration courseRegistration);
}
