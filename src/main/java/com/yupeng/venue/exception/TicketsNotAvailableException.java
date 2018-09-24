package com.yupeng.venue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TicketsNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = 6541418673597623153L;

	public TicketsNotAvailableException(String message) {
		super(message);
	}
}