package com.grirdynamics.yvoronovskyi.carsharing.controller.exception;

import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityNotFoundException;

public class CarRestControllerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CarRestControllerException(String errorMessage) {
        super(errorMessage);
    }

    public CarRestControllerException(String errorMessage, ConstraintViolationException exception) {
        super(errorMessage, exception);
    }
}
