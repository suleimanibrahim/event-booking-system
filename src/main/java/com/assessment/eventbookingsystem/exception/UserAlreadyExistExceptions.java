package com.assessment.eventbookingsystem.exception;

public class UserAlreadyExistExceptions extends RuntimeException{
    public UserAlreadyExistExceptions() {
    }

    public UserAlreadyExistExceptions(String message) {
        super(message);
    }
}
