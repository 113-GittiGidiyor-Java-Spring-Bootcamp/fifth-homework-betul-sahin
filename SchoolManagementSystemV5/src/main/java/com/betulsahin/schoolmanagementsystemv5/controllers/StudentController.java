package com.betulsahin.schoolmanagementsystemv5.controllers;

import com.betulsahin.schoolmanagementsystemv5.dtos.StudentDto;
import com.betulsahin.schoolmanagementsystemv5.entities.Student;
import com.betulsahin.schoolmanagementsystemv5.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody StudentDto request){
        Optional<Student> studentOptional = studentService.create(request);

        if(!studentOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(studentOptional.get(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAll(){
        final List<StudentDto> students = studentService.findAll();

        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentDto> get(@PathVariable long id){
        final StudentDto student = studentService.findById(id);

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Student> update(StudentDto request){
        Optional<Student> studentOptional = studentService.update(request);

        if(!studentOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(studentOptional.get(), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        studentService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
