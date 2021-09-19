package com.betulsahin.schoolmanagementsystemv5.controllers;

import com.betulsahin.schoolmanagementsystemv5.dtos.InstructorDto;
import com.betulsahin.schoolmanagementsystemv5.models.PermanentInstructor;
import com.betulsahin.schoolmanagementsystemv5.models.abstracts.Instructor;
import com.betulsahin.schoolmanagementsystemv5.services.InstructorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstructorControllerTest {
    @Mock
    InstructorService mockInstructorService;

    @InjectMocks
    InstructorController underTest;

    @Test
    void itShouldCreateInstructorSuccessfully(){
        // given
        Instructor instructor = new PermanentInstructor();
        instructor.setPhoneNumber("5553334455");
        Optional<Instructor> expected = Optional.of(instructor);
        when(mockInstructorService.create(any())).thenReturn(expected);

        // when
        InstructorDto request = new InstructorDto();
        Instructor actual = this.underTest.create(request).getBody();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.get(), actual),
                () -> assertEquals(instructor.getPhoneNumber(), actual.getPhoneNumber())
        );
    }
}