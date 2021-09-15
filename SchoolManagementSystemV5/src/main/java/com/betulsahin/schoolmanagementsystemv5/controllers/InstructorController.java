package com.betulsahin.schoolmanagementsystemv5.controllers;

import com.betulsahin.schoolmanagementsystemv5.dtos.InstructorDto;
import com.betulsahin.schoolmanagementsystemv5.models.abstracts.Instructor;
import com.betulsahin.schoolmanagementsystemv5.models.enums.SalaryUpdateType;
import com.betulsahin.schoolmanagementsystemv5.services.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/instructors")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;

    @PostMapping
    public ResponseEntity<Instructor> create(@RequestBody InstructorDto request){
        Optional<Instructor> instructorOptional = instructorService.create(request);

        if(!instructorOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(instructorOptional.get(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InstructorDto>> getAll(){
        final List<InstructorDto> instructors = instructorService.findAll();

        return new ResponseEntity<>(instructors, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<InstructorDto> get(@PathVariable long id){
        final InstructorDto instructor = instructorService.findById(id);

        return new ResponseEntity<>(instructor, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Instructor> update(@RequestBody InstructorDto request){
        Optional<Instructor> instructorOptional = instructorService.update(request);

        if(!instructorOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(instructorOptional.get(), HttpStatus.OK);
    }

    @PutMapping("{id}/{salaryPercentage}/{salaryUpdateType}")
    public ResponseEntity<Instructor> updateSalary(@PathVariable long id,
                                                   @PathVariable double salaryPercentage,
                                                   @PathVariable SalaryUpdateType salaryUpdateType){
        Optional<Instructor> instructorOptional = instructorService.updateSalary(id, salaryPercentage, salaryUpdateType);

        if(!instructorOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(instructorOptional.get(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        instructorService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
