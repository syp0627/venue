package com.yupeng.venue.utils;

import java.util.List;

import com.yupeng.venue.beans.Venue;
import com.yupeng.venue.enitities.Seat;

public interface TicketLookupHelper {
	public List<Seat> findTickets(Venue venue, int numSeats);
}
