package com.betulsahin.schoolmanagementsystemv5.services;

import com.betulsahin.schoolmanagementsystemv5.dtos.CourseDto;
import com.betulsahin.schoolmanagementsystemv5.entities.Course;
import com.betulsahin.schoolmanagementsystemv5.exceptions.CourseIsAlreadyExistException;
import com.betulsahin.schoolmanagementsystemv5.exceptions.CourseNotFoundException;
import com.betulsahin.schoolmanagementsystemv5.mappers.CourseMapper;
import com.betulsahin.schoolmanagementsystemv5.repositories.CourseRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.betulsahin.schoolmanagementsystemv5.utils.ErrorMessageConstants.COURSE_NOT_FOUND;
import static com.betulsahin.schoolmanagementsystemv5.utils.ErrorMessageConstants.FOUND_COURSE;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    /**
     * creates a course to database.
     *
     * @param request the request object of course
     * @return saved course object as Optional
     */
    @Transactional
    public Optional<Course> create(CourseDto request) {
        boolean courseExist = courseRepository.findByCode(request.getCode()).
                isPresent();

        if(courseExist){
            throw new CourseIsAlreadyExistException(
                    String.format(FOUND_COURSE, request.getCode()));
        }

        Course savedCourse = courseRepository.save(
                courseMapper.map(request));

        return Optional.of(savedCourse);
    }

    /**
     * finds all courses.
     *
     * @return a list of course dto
     */
    @Transactional(readOnly = true)
    public List<CourseDto> findAll(){
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * finds the course object by id.
     *
     * @param id the identity of the course
     * @return the found course
     */
    @Transactional(readOnly = true)
    public CourseDto findById(long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(
                    String.format(COURSE_NOT_FOUND, id)));

        return courseMapper.mapToDto(course);
    }

    /**
     * updates a course to database.
     *
     * @param request the request object of course
     * @return updated course object as Optional
     */
    @Transactional
    public Optional<Course> update(CourseDto request){
        Course selectedCourse = courseRepository.findById(request.getId())
                .orElseThrow(() -> new CourseNotFoundException(
                    String.format(COURSE_NOT_FOUND, request.getId())));

        boolean courseExist = courseRepository.findByCode(request.getCode()).
                isPresent();

        if(courseExist){
            throw new CourseIsAlreadyExistException(
                    String.format(FOUND_COURSE, request.getCode()));
        }

        Course updatedCourse = courseRepository.save(selectedCourse);

        return Optional.of(updatedCourse);
    }

    /**
     * deletes the course object by id.
     *
     * @param id the identity of the course object
     */
    @Transactional(readOnly = true)
    public void deleteById(long id){
        Course selectedCourse = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(
                    String.format(COURSE_NOT_FOUND, id)));

        courseRepository.delete(selectedCourse);
    }
}
