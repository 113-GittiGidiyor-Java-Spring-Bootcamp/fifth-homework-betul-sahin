package com.betulsahin.schoolmanagementsystemv5.mappers;

import com.betulsahin.schoolmanagementsystemv5.dtos.StudentDto;
import com.betulsahin.schoolmanagementsystemv5.entities.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student map(StudentDto studentDto);
    StudentDto mapToDto(Student student);
}
