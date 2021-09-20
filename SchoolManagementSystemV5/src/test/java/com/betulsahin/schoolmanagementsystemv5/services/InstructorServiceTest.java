package com.betulsahin.schoolmanagementsystemv5.services;

import com.betulsahin.schoolmanagementsystemv5.dtos.InstructorDto;
import com.betulsahin.schoolmanagementsystemv5.dtos.PermanentInstructorDto;
import com.betulsahin.schoolmanagementsystemv5.exceptions.InstructorIsAlreadyExistException;
import com.betulsahin.schoolmanagementsystemv5.mappers.InstructorMapper;
import com.betulsahin.schoolmanagementsystemv5.models.PermanentInstructor;
import com.betulsahin.schoolmanagementsystemv5.models.abstracts.Instructor;
import com.betulsahin.schoolmanagementsystemv5.repositories.InstructorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstructorServiceTest {

    @Mock
    private InstructorRepository mockInstructorRepository;

    @Mock
    private InstructorMapper mockInstructorMapper;

    @InjectMocks
    private InstructorService underTest;

    @Test
    void itShouldFindAllInstructor(){
        // given
        Instructor instructor = new PermanentInstructor();
        instructor.setName("Ahmet Yılmaz");
        List<Instructor> instructors = Collections.singletonList(instructor);

        InstructorDto request = new InstructorDto();
        request.setName(instructor.getName());

        Mockito.when(mockInstructorMapper.mapToDto(any())).
                thenReturn(request);

        Mockito.when(mockInstructorRepository.findAll()).
                thenReturn(instructors);

        // when
        List<InstructorDto> actual = underTest.findAll();

        // then
        assertEquals(actual.get(0).getName(), "Ahmet Yılmaz");
    }

    @Test
    void itShouldReturnInstructor_whenInstructorIdExist(){
        // given
        Instructor instructor = new PermanentInstructor();

        InstructorDto request = new InstructorDto();

        Mockito.when(mockInstructorMapper.mapToDto(any())).
                thenReturn(request);

        Mockito.when(mockInstructorRepository.findById(1L)).
                thenReturn(Optional.of(instructor));

        // when
        InstructorDto actual = underTest.findById(1L);

        // then
        assertEquals(actual, request);
    }

    @Test
    void itShouldCreateNewInstructor(){
        // given
        Instructor instructor = new PermanentInstructor();
        instructor.setPhoneNumber("5554443322");

        InstructorDto request = new InstructorDto();
        request.setPhoneNumber(instructor.getPhoneNumber());

        Mockito.when(mockInstructorRepository.findByPhoneNumber(any())).
                thenReturn(Optional.empty());

        Mockito.when(mockInstructorRepository.save(any())).thenReturn(instructor);

        // when
        Instructor actual = underTest.create(request).get();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(instructor, actual),
                () -> assertEquals(instructor.getPhoneNumber(), actual.getPhoneNumber())
        );
    }

    @Test
    void itShouldNotCreateInstructor_whenInstructorExist(){
        // given
        Instructor instructor = new PermanentInstructor();
        instructor.setPhoneNumber("5554443322");

        InstructorDto request = new InstructorDto();
        request.setPhoneNumber(instructor.getPhoneNumber());

        Mockito.when(mockInstructorRepository.findByPhoneNumber(instructor.getPhoneNumber())).
                thenReturn(Optional.of(instructor));

        // when
        Executable executable = () -> this.underTest.create(request).get();

        // then
        assertThrows(InstructorIsAlreadyExistException.class, executable);
    }
}