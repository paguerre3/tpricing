package com.capitol.pricing.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionAdvice {

    private RestError fulfillError(final Exception ex, final WebRequest request) {
        return new RestError.Builder().setMessage(ex.getMessage())
                .setDetails(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<?> handleItemNotFoundException(ItemNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(fulfillError(ex, request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingArgumentException.class)
    public ResponseEntity<?> handleMissingArgumentException(MissingArgumentException ex, WebRequest request) {
        return new ResponseEntity<>(fulfillError(ex, request), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(fulfillError(ex, request), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
