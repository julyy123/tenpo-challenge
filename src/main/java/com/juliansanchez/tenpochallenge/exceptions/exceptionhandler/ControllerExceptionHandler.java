package com.juliansanchez.tenpochallenge.exceptions.exceptionhandler;

import com.juliansanchez.tenpochallenge.exceptions.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * Controller exception handler class
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(final BadCredentialsException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputException(final InvalidInputException exception) {
        return ResponseEntity.unprocessableEntity().body(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(final ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        String errorMessage;

        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder
                    .append(violation.getMessage())
                    .append("\n"));
            errorMessage = builder.toString();
        } else
            errorMessage = "ConstraintViolationException occurred.";

        return ResponseEntity.unprocessableEntity().body(errorMessage);
    }
}
