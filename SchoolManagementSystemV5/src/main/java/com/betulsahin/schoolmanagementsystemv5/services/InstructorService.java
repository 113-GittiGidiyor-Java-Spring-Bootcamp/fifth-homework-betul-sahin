package com.betulsahin.schoolmanagementsystemv5.services;

import com.betulsahin.schoolmanagementsystemv5.dtos.InstructorDto;
import com.betulsahin.schoolmanagementsystemv5.models.InstructorServiceTransactionLogger;
import com.betulsahin.schoolmanagementsystemv5.models.abstracts.Instructor;
import com.betulsahin.schoolmanagementsystemv5.models.PermanentInstructor;
import com.betulsahin.schoolmanagementsystemv5.models.VisitingResearcher;
import com.betulsahin.schoolmanagementsystemv5.models.enums.SalaryUpdateType;
import com.betulsahin.schoolmanagementsystemv5.exceptions.InstructorIsAlreadyExistException;
import com.betulsahin.schoolmanagementsystemv5.exceptions.InstructorNotFoundException;
import com.betulsahin.schoolmanagementsystemv5.mappers.InstructorMapper;
import com.betulsahin.schoolmanagementsystemv5.repositories.InstructorRepository;
import com.betulsahin.schoolmanagementsystemv5.repositories.InstructorServiceTransactionLoggerRepository;
import com.betulsahin.schoolmanagementsystemv5.utils.ClientRequestInfo;
import com.betulsahin.schoolmanagementsystemv5.utils.PayrollUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.betulsahin.schoolmanagementsystemv5.utils.ErrorMessageConstants.FOUND_INSTRUCTOR;
import static com.betulsahin.schoolmanagementsystemv5.utils.ErrorMessageConstants.INSTRUCTOR_NOT_FOUND;

@Service
public class InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private InstructorServiceTransactionLoggerRepository instructorServiceTransactionLoggerRepository;
    @Autowired
    private InstructorMapper instructorMapper;
    @Autowired
    private ClientRequestInfo clientRequestInfo;

    /**
     * finds all instructor from database.
     *
     * @return a list of instructors
     */
    @Transactional(readOnly = true)
    public List<InstructorDto> findAll() {
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
    public InstructorDto findById(long id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() -> new InstructorNotFoundException(
                String.format(INSTRUCTOR_NOT_FOUND, id)));

        return instructorMapper.mapToDto(instructor);
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
    public Optional<Instructor> update(InstructorDto request) {
        Instructor selectedInstructor = instructorRepository.findById(request.getId()).orElseThrow(() -> new InstructorNotFoundException(
                String.format(INSTRUCTOR_NOT_FOUND, request.getId())));

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
     * updates salary of the instructor by given salary rate.
     *
     * @param id         the identity of the instructor
     * @param salaryRate for updating salary of the instructor
     * @return updated instructor object as Optional
     */
    @Transactional
    public Optional<Instructor> updateSalary(long id, double salaryPercentage, SalaryUpdateType salaryUpdateType) {
        Instructor selectedInstructor = instructorRepository.findById(id).orElseThrow(() -> new InstructorNotFoundException(
                String.format(INSTRUCTOR_NOT_FOUND, id)));

        double salaryBefore = selectedInstructor.getSalary();
        double payout = PayrollUtil.calculateSalary(selectedInstructor, salaryPercentage, salaryUpdateType);
        if (selectedInstructor instanceof PermanentInstructor) {
            ((PermanentInstructor) selectedInstructor).setFixedSalary(payout);
        } else if (selectedInstructor instanceof VisitingResearcher) {
            ((VisitingResearcher) selectedInstructor).setHourlySalary(payout);
        }

        Instructor updatedInstructor = instructorRepository.save(selectedInstructor);

        // logs salary update transactions
        this.saveSalaryUpdateTransactions(updatedInstructor, salaryBefore, salaryPercentage, salaryUpdateType);

        return Optional.of(updatedInstructor);
    }

    /**
     * deletes the instructor object by id.
     *
     * @param id the identity of the instructor object
     */
    @Transactional(readOnly = true)
    public void deleteById(long id) {
        Instructor selectedInstructor = instructorRepository.findById(id).orElseThrow(() -> new InstructorNotFoundException(
                String.format(INSTRUCTOR_NOT_FOUND, id)));
        instructorRepository.delete(selectedInstructor);
    }

    private void saveSalaryUpdateTransactions(Instructor instructor,
                                              double salaryBefore,
                                              double salaryPercentage,
                                              SalaryUpdateType salaryUpdateType){
        InstructorServiceTransactionLogger transactionLogger = new InstructorServiceTransactionLogger();

        transactionLogger.setInstructorId(instructor.getId());
        transactionLogger.setSalaryBeforeUpdate(salaryBefore);
        transactionLogger.setSalaryAfterUpdate(instructor.getSalary());
        transactionLogger.setSalaryPercentage(salaryPercentage);
        transactionLogger.setRequestDateTime(LocalDate.now());
        transactionLogger.setClientIpAdress(clientRequestInfo.getClientIpAdress());
        transactionLogger.setClientUrl(clientRequestInfo.getClientUrl());
        transactionLogger.setSessionActivityId(clientRequestInfo.getSessionActivityId());
        transactionLogger.setSalaryUpdateType(salaryUpdateType);
        this.instructorServiceTransactionLoggerRepository.save(transactionLogger);
    }
}

