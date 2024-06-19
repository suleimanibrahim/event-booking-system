package com.assessment.eventbookingsystem.exception;

public class DuplicateRecordExceptions extends RuntimeException{
    public DuplicateRecordExceptions() {
    }

    public DuplicateRecordExceptions(String message) {
        super(message);
    }
}
