package com.assessment.eventbookingsystem.exception;

public class TicketAlreadyBookException extends RuntimeException {
    public TicketAlreadyBookException() {
    }

    public TicketAlreadyBookException(String message) {
        super(message);
    }
}
