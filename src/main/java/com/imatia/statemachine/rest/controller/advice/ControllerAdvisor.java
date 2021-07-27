package com.imatia.statemachine.rest.controller.advice;

import com.imatia.statemachine.domain.exception.ImpossibleStatusUpdateException;
import com.imatia.statemachine.domain.exception.StatusNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Specialization of {@link Component} that allows to handle exceptions across the whole application in one
 * global handling component.
 *
 * @author Jesús Iglesias
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(StatusNotFoundException.class)
    public ResponseEntity<Object> handleStatusNotFoundException(StatusNotFoundException e, WebRequest request) {
        return handleExceptionInternal(e, e.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ImpossibleStatusUpdateException.class)
    public ResponseEntity<Object> handleStatusNotFoundException(ImpossibleStatusUpdateException e, WebRequest request) {
        return handleExceptionInternal(e, e.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
