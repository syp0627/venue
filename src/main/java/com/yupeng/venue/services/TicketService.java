package com.yupeng.venue.services;

import com.yupeng.venue.models.SeatHold;

public interface TicketService {
	
	int numSeatsAvailable();
	
	SeatHold findAndHoldSeats(int numSeats, String customerEmail);
	
	String reverveSeats(int seatHoldId, String customerEmail);
}
