package com.capitol.pricing.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionAdvice {
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<?> handleItemNotFoundException(ItemNotFoundException ex, WebRequest request) {
        RestError ed = new RestError.Builder().setMessage(ex.getMessage())
                .setDetails(request.getDescription(false))
                .build();
        return new ResponseEntity<>(ed, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex, WebRequest request) {
        RestError ed = new RestError.Builder().setMessage(ex.getMessage())
                .setDetails(request.getDescription(false))
                .build();
        return new ResponseEntity<>(ed, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
