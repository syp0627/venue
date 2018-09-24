package com.yupeng.venue.services;

import java.util.List;

import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.models.SeatsStatus;

public interface SeatsLookupService {
	
	void refreshCache();
	
	int numSeatsAvailable();
	
	List<Seat> findSeats(int numSeats);
	
	SeatsStatus getSeatsStatus();
}
