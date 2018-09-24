package com.yupeng.venue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TicketsHoldInfoNotValidedException extends RuntimeException {

	private static final long serialVersionUID = 4428428239543985284L;

	public TicketsHoldInfoNotValidedException() {
		super();
	}

	public TicketsHoldInfoNotValidedException(String message) {
		super(message);
	}

}
