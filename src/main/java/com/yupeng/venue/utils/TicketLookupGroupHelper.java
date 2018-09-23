package com.yupeng.venue.utils;

import java.util.List;

import com.yupeng.venue.enitities.Seat;

public interface TicketLookupGroupHelper {
	public List<Seat> findTicketsInGroup(Seat[] seats, int column, int[]priorityMap, int[] priorityIndex, int numSeats);
}
