package com.assessment.eventbookingsystem.exception;

public class ViolationException extends RuntimeException{
    public ViolationException() {
    }

    public ViolationException(String message) {
        super(message);
    }
}
