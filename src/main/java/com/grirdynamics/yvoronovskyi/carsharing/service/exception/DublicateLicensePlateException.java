package com.grirdynamics.yvoronovskyi.carsharing.service.exception;

public class DublicateLicensePlateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DublicateLicensePlateException(String message) {
        super(message);
    }
}
