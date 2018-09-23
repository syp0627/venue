package com.yupeng.venue.services;

import com.yupeng.venue.models.SeatHold;

public interface TicketHoldService {
	SeatHold holdSeats(int numSeats, String customerEmail);

	SeatHold getSeatHold(int seatHoldId, String customerEmail);
}
