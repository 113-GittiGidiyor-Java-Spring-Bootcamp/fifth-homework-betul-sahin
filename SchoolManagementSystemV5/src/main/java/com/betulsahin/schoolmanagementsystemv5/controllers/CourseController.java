package com.betulsahin.schoolmanagementsystemv5.controllers;

import com.betulsahin.schoolmanagementsystemv5.dtos.CourseDto;
import com.betulsahin.schoolmanagementsystemv5.models.Course;
import com.betulsahin.schoolmanagementsystemv5.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody CourseDto request){
        Optional<Course> courseOptional = courseService.create(request);

        if(!courseOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(courseOptional.get(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAll(){
        final List<CourseDto> courses = courseService.findAll();

        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CourseDto> get(@PathVariable long id){
        final CourseDto course = courseService.findById(id);

        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Course> update(@RequestBody CourseDto request){
        Optional<Course> courseOptional = courseService.update(request);

        if(!courseOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(courseOptional.get(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        courseService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
