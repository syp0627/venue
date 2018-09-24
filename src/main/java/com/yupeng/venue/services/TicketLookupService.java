package com.yupeng.venue.services;

import java.util.List;

import com.yupeng.venue.enitities.Seat;

public interface TicketLookupService {
	
	void refreshCache();
	
	int numSeatsAvailable();
	
	List<Seat> findSeats(int numSeats);
}
