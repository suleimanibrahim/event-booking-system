package com.assessment.eventbookingsystem.exception;

public class InvalidDetailsExceptions extends RuntimeException{
    public InvalidDetailsExceptions() {
    }

    public InvalidDetailsExceptions(String message) {
        super(message);
    }
}
