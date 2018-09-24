package com.yupeng.venue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class TicketsReserveFailedException extends RuntimeException {

	private static final long serialVersionUID = -7255316944921985029L;

	public TicketsReserveFailedException(String message) {
		super(message);
	}
}