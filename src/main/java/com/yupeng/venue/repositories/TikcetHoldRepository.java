package com.yupeng.venue.repositories;

import com.yupeng.venue.models.SeatHold;

public interface TikcetHoldRepository {
	public boolean save(SeatHold seatHold);
	
	public SeatHold get(int seatHoldId);
}
