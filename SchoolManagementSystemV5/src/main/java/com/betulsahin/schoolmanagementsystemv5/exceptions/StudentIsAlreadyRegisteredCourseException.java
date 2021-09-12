package com.betulsahin.schoolmanagementsystemv5.exceptions;

public class StudentIsAlreadyRegisteredCourseException extends RuntimeException{
    public StudentIsAlreadyRegisteredCourseException(String message){
        super(message);
    }
}
