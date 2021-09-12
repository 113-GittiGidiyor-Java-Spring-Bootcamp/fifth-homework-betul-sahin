package com.betulsahin.schoolmanagementsystemv5.services;

import com.betulsahin.schoolmanagementsystemv5.dtos.InstructorDto;
import com.betulsahin.schoolmanagementsystemv5.entities.Instructor;
import com.betulsahin.schoolmanagementsystemv5.exceptions.InstructorIsAlreadyExistException;
import com.betulsahin.schoolmanagementsystemv5.exceptions.InstructorNotFoundException;
import com.betulsahin.schoolmanagementsystemv5.mappers.InstructorMapper;
import com.betulsahin.schoolmanagementsystemv5.repositories.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.betulsahin.schoolmanagementsystemv5.utils.ErrorMessageConstants.FOUND_INSTRUCTOR;
import static com.betulsahin.schoolmanagementsystemv5.utils.ErrorMessageConstants.INSTRUCTOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;

    /**
     * finds all instructor from database.
     *
     * @return a list of instructors
     */
    @Transactional(readOnly = true)
    public List<InstructorDto> findAll(){
        return instructorRepository.findAll()
                .stream()
                .map(instructorMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * finds the instructor object by id.
     *
     * @param id the identity of the course
     * @return the found instructor object
     */
    @Transactional(readOnly = true)
    public Instructor findById(long id){
        return instructorRepository.findById(id).orElseThrow(() -> new InstructorNotFoundException(
                        String.format(INSTRUCTOR_NOT_FOUND, id)));
    }

    /**
     * creates an instructor to database.
     *
     * @param request the request object of instructor
     * @return saved instructor as optional
     */
    @Transactional
    public Optional<Instructor> create(InstructorDto request) {
        boolean instructorExist = instructorRepository.
                findByPhoneNumber(request.getPhoneNumber()).
                isPresent();

        if (instructorExist) {
            throw new InstructorIsAlreadyExistException(
                    String.format(FOUND_INSTRUCTOR, request.getPhoneNumber()));
        }

        Instructor savedInstructor = instructorRepository.save(
                instructorMapper.map(request));

        return Optional.of(savedInstructor);
    }

    /**
     * updates a instructor to database.
     *
     * @param request the request object of course
     * @return updated instructor object as Optional
     */
    @Transactional
    public Optional<Instructor> update(InstructorDto request){
        Instructor selectedInstructor = this.findById(request.getId());

        boolean instructorExist = instructorRepository.
                findByPhoneNumber(request.getPhoneNumber()).
                isPresent();

        if (instructorExist) {
            throw new InstructorIsAlreadyExistException(
                    String.format(FOUND_INSTRUCTOR, request.getPhoneNumber()));
        }

        Instructor updatedInstructor = instructorRepository.save(
                selectedInstructor);

        return Optional.of(updatedInstructor);
    }

    /**
     * deletes the instructor object by id.
     *
     * @param id the identity of the instructor object
     */
    @Transactional(readOnly = true)
    public void deleteById(long id){
        Instructor selectedInstructor = this.findById(id);
        instructorRepository.delete(selectedInstructor);
    }
}
