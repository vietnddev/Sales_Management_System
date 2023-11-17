package com.flowiee.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataExistsException extends RuntimeException {
	public DataExistsException(String message) {
		super(message);
	}
}