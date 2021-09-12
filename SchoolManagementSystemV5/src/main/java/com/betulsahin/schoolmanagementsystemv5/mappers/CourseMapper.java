package com.betulsahin.schoolmanagementsystemv5.mappers;

import com.betulsahin.schoolmanagementsystemv5.dtos.CourseDto;
import com.betulsahin.schoolmanagementsystemv5.entities.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    Course map(CourseDto courseDto);
    CourseDto mapToDto(Course course);
}
