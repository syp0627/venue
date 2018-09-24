package com.yupeng.venue.utils;

import java.util.List;

import com.yupeng.venue.enitities.Seat;

public interface SeatsLookupOneLineHelper {
	public List<Seat> findSeatsInOneLine(Seat[] seats, int column, int[] priorityIndex, int numSeats);
}
