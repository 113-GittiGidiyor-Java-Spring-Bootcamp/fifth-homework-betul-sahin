package com.betulsahin.schoolmanagementsystemv5.exceptions;

public class CourseIsAlreadyExistException extends RuntimeException{
    public CourseIsAlreadyExistException(String message){
        super(message);
    }
}
