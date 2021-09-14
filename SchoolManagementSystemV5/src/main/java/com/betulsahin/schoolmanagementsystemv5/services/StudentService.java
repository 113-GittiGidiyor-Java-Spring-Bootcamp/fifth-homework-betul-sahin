package com.betulsahin.schoolmanagementsystemv5.services;

import com.betulsahin.schoolmanagementsystemv5.dtos.StudentDto;
import com.betulsahin.schoolmanagementsystemv5.entities.Student;
import com.betulsahin.schoolmanagementsystemv5.exceptions.StudentNotFoundException;
import com.betulsahin.schoolmanagementsystemv5.mappers.StudentMapper;
import com.betulsahin.schoolmanagementsystemv5.repositories.StudentRepository;
import com.betulsahin.schoolmanagementsystemv5.utils.StudentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.betulsahin.schoolmanagementsystemv5.utils.ErrorMessageConstants.STUDENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    /**
     * gets all student from database.
     *
     * @return a list of student dto
     */
    @Transactional(readOnly = true)
    public List<StudentDto> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * gets student object by id.
     *
     * @param id the identity of the student
     * @return the found student object
     */
    @Transactional(readOnly = true)
    public StudentDto findById(long id) {
        Student student = studentRepository.findById(id).
                orElseThrow(() -> new StudentNotFoundException(
                        String.format(STUDENT_NOT_FOUND, id)));

        return studentMapper.mapToDto(student);
    }

    /**
     * creates a student to database.
     *
     * @param request the request object of student
     * @return saved student as optional
     */
    @Transactional
    public Optional<Student> create(StudentDto request) {

        this.validateRequest(request);

        Student savedStudent = studentRepository.save(
                studentMapper.map(request));

        return Optional.of(savedStudent);
    }

    /**
     * updates a student to database
     *
     * @param request the request object of student
     * @return updated student as optional
     */
    @Transactional
    public Optional<Student> update(StudentDto request) {

        Student selectedStudent = studentRepository.findById(request.getId()).
                orElseThrow(() -> new StudentNotFoundException(
                        String.format(STUDENT_NOT_FOUND, request.getId())));

        this.validateRequest(request);

        Student updatedStudent = studentRepository.save(selectedStudent);

        return Optional.of(updatedStudent);
    }

    /**
     * validates given request the appropiate of the student age range (18 to 40) or not.
     *
     * @param request the request object of student
     */
    private void validateRequest(StudentDto request) {
        StudentValidator.validateAge(request.getBirthdate());
    }

    /**
     * deletes student object by id.
     *
     * @param id the identity of the student
     */
    @Transactional(readOnly = true)
    public void deleteById(long id) {
        Student selectStudent = studentRepository.findById(id).
                orElseThrow(() -> new StudentNotFoundException(
                        String.format(STUDENT_NOT_FOUND, id)));

        studentRepository.delete(selectStudent);
    }

}
