package com.betulsahin.schoolmanagementsystemv5.controllers;

import com.betulsahin.schoolmanagementsystemv5.dtos.CourseRegistrationDto;
import com.betulsahin.schoolmanagementsystemv5.entities.CourseRegistration;
import com.betulsahin.schoolmanagementsystemv5.services.CourseRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/course-registration")
@RequiredArgsConstructor
public class CourseRegistrationController {
    private final CourseRegistrationService courseRegistrationService;

    @PostMapping
    public ResponseEntity<CourseRegistration> create(@RequestBody CourseRegistrationDto request) {
        Optional<CourseRegistration> courseRegistrationOptional = courseRegistrationService.create(request);

        if (!courseRegistrationOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(courseRegistrationOptional.get(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CourseRegistrationDto>> getAll(){
        final List<CourseRegistrationDto> courseRegistrations = courseRegistrationService.findAll();

        return new ResponseEntity<>(courseRegistrations, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CourseRegistrationDto> get(@PathVariable long id){
        final CourseRegistrationDto courseRegistration = courseRegistrationService.findById(id);

        return new ResponseEntity<>(courseRegistration, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CourseRegistration> update(@RequestBody CourseRegistrationDto request) {
        Optional<CourseRegistration> courseRegistrationOptional = courseRegistrationService.update(request);

        if (!courseRegistrationOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(courseRegistrationOptional.get(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        courseRegistrationService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
