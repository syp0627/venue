package com.yupeng.venue.repositories;

import java.util.List;

import com.yupeng.venue.enitities.Seat;

public interface VenueRepositry {
	
	public Seat[][] getSeats(); 
	
	public boolean holdSeats(List<Seat> seatList); 
	
	public boolean releaseHoldSeats(List<Integer> seatList); 
	
	public void reserveSeats(List<Integer> seatList); 
	
}
