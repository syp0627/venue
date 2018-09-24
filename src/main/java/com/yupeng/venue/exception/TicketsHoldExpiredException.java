package com.yupeng.venue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TicketsHoldExpiredException extends RuntimeException {

	private static final long serialVersionUID = 1707968296408325031L;

	public TicketsHoldExpiredException() {
		super();
	}

	public TicketsHoldExpiredException(String message) {
		super(message);
	}
}
