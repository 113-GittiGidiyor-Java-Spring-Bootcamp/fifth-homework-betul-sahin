package com.betulsahin.schoolmanagementsystemv5.utils;

import com.betulsahin.schoolmanagementsystemv5.exceptions.StudentAgeNotValidException;

import java.time.LocalDate;
import java.time.Period;

public class StudentValidator {

    public static void validateAge(LocalDate birthdate) {
        Period period = Period.between(birthdate, LocalDate.now());
        int age = period.getYears();

        if(age < 18 || age > 40){
            throw new StudentAgeNotValidException(
                    String.format(ErrorMessageConstants.NOT_APPROPRIATE_AGE_RANGE, age));
        }
    }
}
