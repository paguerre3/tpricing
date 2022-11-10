package com.capitol.pricing.except;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@ControllerAdvice
public class GeneralExceptionAdvice {
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<?> handleItemNotFoundException(ItemNotFoundException ex, WebRequest request) {
        ErrorDisplay ed = new ErrorDisplay(LocalDate.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(ed, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex, WebRequest request) {
        ErrorDisplay ed = new ErrorDisplay(LocalDate.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(ed, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
