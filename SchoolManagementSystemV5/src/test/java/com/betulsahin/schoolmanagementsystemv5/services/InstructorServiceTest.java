package com.betulsahin.schoolmanagementsystemv5.services;

import com.betulsahin.schoolmanagementsystemv5.dtos.InstructorDto;
import com.betulsahin.schoolmanagementsystemv5.dtos.PermanentInstructorDto;
import com.betulsahin.schoolmanagementsystemv5.mappers.InstructorMapper;
import com.betulsahin.schoolmanagementsystemv5.models.PermanentInstructor;
import com.betulsahin.schoolmanagementsystemv5.models.abstracts.Instructor;
import com.betulsahin.schoolmanagementsystemv5.repositories.InstructorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void itShouldCreateNewInstructor(){
        // given
        Instructor instructor = new PermanentInstructor();
        instructor.setPhoneNumber("5554443322");

        Mockito.when(mockInstructorRepository.findByPhoneNumber(any())).
                thenReturn(Optional.empty());

        Mockito.when(mockInstructorMapper.map(any())).
                thenReturn(instructor);

        Mockito.when(mockInstructorRepository.save(any())).thenReturn(instructor);

        // when
        InstructorDto request = new PermanentInstructorDto();
        Instructor actual = underTest.create(request).get();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(instructor, actual),
                () -> assertEquals(instructor.getPhoneNumber(), actual.getPhoneNumber())
        );
    }
}