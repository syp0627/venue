package com.yupeng.venue.utils;

import java.util.List;

import com.yupeng.venue.enitities.Seat;

public interface TicketLookupOneLineHelper {
	public List<Seat> findTicketsInOneLine(Seat[] seats, int column, int[] priorityIndex, int numSeats);
}
