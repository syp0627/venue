package com.yupeng.venue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class TicketsHoldFailedException extends RuntimeException {

	private static final long serialVersionUID = -240923047812488250L;

	public TicketsHoldFailedException(String message) {
		super(message);
	}
}