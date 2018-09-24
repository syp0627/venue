package com.yupeng.venue.repositories;

import java.util.List;

import com.yupeng.venue.models.SeatHold;

public interface TikcetHoldRepository {
	public SeatHold save(String customerEmail, long expired, List<Integer> seatsIndex);
	
	public SeatHold get(int seatHoldId);
}
