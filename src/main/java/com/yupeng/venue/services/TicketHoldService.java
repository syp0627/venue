package com.yupeng.venue.services;

import com.yupeng.venue.models.SeatHold;

public interface TicketHoldService {
	SeatHold holdSeats(int numSeats, String customerEmail);

	SeatHold getHoldSeats(int seatHoldId, String customerEmail);
	
	void setHoldExpiredUnit(int holdExpiredUnit);

	void setHoldExpiredDuration(int holdExpiredDuration);
	
	long getHoldMilliscond();
}
