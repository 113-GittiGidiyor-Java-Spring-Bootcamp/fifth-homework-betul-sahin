package com.betulsahin.schoolmanagementsystemv5.services;

import com.betulsahin.schoolmanagementsystemv5.dtos.CourseRegistrationDto;
import com.betulsahin.schoolmanagementsystemv5.entities.CourseRegistration;
import com.betulsahin.schoolmanagementsystemv5.exceptions.CourseRegistrationNotFoundException;
import com.betulsahin.schoolmanagementsystemv5.exceptions.StudentIsAlreadyRegisteredCourseException;
import com.betulsahin.schoolmanagementsystemv5.exceptions.StudentNumberForOneCourseExceededException;
import com.betulsahin.schoolmanagementsystemv5.mappers.CourseRegistrationMapper;
import com.betulsahin.schoolmanagementsystemv5.repositories.CourseRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.betulsahin.schoolmanagementsystemv5.utils.ErrorMessageConstants.*;

@Service
@RequiredArgsConstructor
public class CourseRegistrationService {
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseRegistrationMapper courseRegistrationMapper;

    /**
     * finds all registrations from database.
     *
     * @return a list of registrations
     */
    @Transactional(readOnly = true)
    public List<CourseRegistrationDto> findAll(){
        return courseRegistrationRepository.findAll()
                .stream()
                .map(courseRegistrationMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * finds the registration object by id.
     *
     * @param id the identity of the registration
     * @return the found registration object
     */
    @Transactional(readOnly = true)
    public CourseRegistration findById(long id){
        return courseRegistrationRepository.findById(id).orElseThrow(
                () -> new CourseRegistrationNotFoundException(String.format(COURSEREGISTRATION_NOT_FOUND, id)));
    }

    /**
     * Creates a course registration for a student.
     *
     * @param request the request object of CourseRegistration
     * @return saved CourseRegistration object as Optional
     */
    @Transactional
    public Optional<CourseRegistration> create(CourseRegistrationDto request){
        // Ogrenci bu kursa daha önce kayit olmus mu ?
        boolean registrationExist = courseRegistrationRepository.findByStudentIdAndCourseId(
                        request.getStudentId(), request.getCourseId()).
                isPresent();

        if(registrationExist){
            String exceptionMessage = String.format(FOUND_REGISTERED_STUDENT, request.getStudentId());
            throw new StudentIsAlreadyRegisteredCourseException(exceptionMessage);

        }

        // Bu kursa kayit olan ogrenci sayisi 20'den fazla mi ?
        int studentCountOfRegistered = courseRegistrationRepository.
                findStudentCountByCourse(request.getCourseId());

        if(studentCountOfRegistered > 20){
            throw new StudentNumberForOneCourseExceededException(
                    String.format(COURSE_EXCEEDED, studentCountOfRegistered));
        }

        CourseRegistration savedRegistrations = courseRegistrationRepository.save(
                courseRegistrationMapper.map(request));

        return Optional.of(savedRegistrations);
    }

    /**
     * Updates a course registration for a student.
     *
     * @param request the request object of CourseRegistration
     * @return updated CourseRegistration object as Optional
     */
    @Transactional
    public Optional<CourseRegistration> update(CourseRegistrationDto request){
        CourseRegistration selectedCourseRegistration = this.findById(request.getId());

        // Has the student registered for this course before ?
        boolean registrationExist = courseRegistrationRepository.findByStudentIdAndCourseId(
                        request.getStudentId(), request.getCourseId()).
                isPresent();

        if(registrationExist){
            String exceptionMessage = String.format(FOUND_REGISTERED_STUDENT, request.getStudentId());
            throw new StudentIsAlreadyRegisteredCourseException(exceptionMessage);

        }

        // Is the number of students enrolled this course more than 20 ?
        int studentCountOfRegistered = courseRegistrationRepository.
                findStudentCountByCourse(request.getCourseId());

        if(studentCountOfRegistered > 20){
            throw new StudentNumberForOneCourseExceededException(
                    String.format(COURSE_EXCEEDED, studentCountOfRegistered));
        }

        CourseRegistration updatedCourseRegistration = courseRegistrationRepository.
                save(selectedCourseRegistration);

        return Optional.of(updatedCourseRegistration);
    }

    /**
     * deletes the registration object by id.
     *
     * @param id the identity of the registration object
     */
    @Transactional(readOnly = true)
    public void deleteById(long id){
        CourseRegistration selectedCourseRegistration = this.findById(id);
        courseRegistrationRepository.delete(selectedCourseRegistration);
    }
}
