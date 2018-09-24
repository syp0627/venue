package com.yupeng.venue.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.yupeng.venue.models.SeatHold;
import com.yupeng.venue.services.TicketService;

@RestController
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@GetMapping("/available")
	public int hold() {
		return ticketService.numSeatsAvailable();
	}

	@GetMapping("/hold/{email}/{numSeats}")
	public SeatHold hold(@PathVariable String email, @PathVariable int numSeats) {
		return ticketService.findAndHoldSeats(numSeats, email);
	}

	@GetMapping("/reserve/{email}/{seatHoldId}")
	public String reserve(@PathVariable String email, @PathVariable int seatHoldId) {
		return ticketService.reverveSeats(seatHoldId, email);
	}
}
