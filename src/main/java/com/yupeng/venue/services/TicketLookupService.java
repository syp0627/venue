package com.yupeng.venue.services;

import java.util.List;

import com.yupeng.venue.enitities.Seat;

public interface TicketLookupService {
	
	int numSeatsAvailable();
	
	List<Seat> findSeats(int numSeats);
}
