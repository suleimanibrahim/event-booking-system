package com.assessment.eventbookingsystem.exception;

import com.assessment.eventbookingsystem.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecordNotFoundException.class)
    ResponseEntity<ApiResponse<String>> handleNotFoundException(RecordNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
    }
    @ExceptionHandler(ViolationException.class)
    ResponseEntity<ApiResponse<String>> handleValidationException(ViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
    }
    @ExceptionHandler(InvalidDetailsExceptions.class)
    ResponseEntity<ApiResponse<String>> handleInvalidDetailsException(InvalidDetailsExceptions e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
    }
    @ExceptionHandler(TicketAlreadyBookException.class)
    ResponseEntity<ApiResponse<String>> handleBookedTicketException(TicketAlreadyBookException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
    }

    @ExceptionHandler(UserAlreadyExistExceptions.class)
    ResponseEntity<ApiResponse<String>> handleUserAlreadyExistException(UserAlreadyExistExceptions e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
    }

    @ExceptionHandler(DuplicateRecordExceptions.class)
    ResponseEntity<ApiResponse<String>> handleDuplicateRecordException(DuplicateRecordExceptions e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "Validation failed";
        return ResponseEntity.badRequest().body(new ApiResponse<>(errorMessage, HttpStatus.BAD_REQUEST.value(), null));
    }
}
