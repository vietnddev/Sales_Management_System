package com.flowiee.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class DataInUseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataInUseException(String message) {
        super(message);
    }
}