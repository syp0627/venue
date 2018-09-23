package com.yupeng.venue.beans;

import com.yupeng.venue.enitities.Seat;

public interface Venue {

	public Seat[] getSeats();

	public int[] getPriorityMap();

	public int[] getPriorityIndex();

	public int numSeatsAvailable();
	
	public int getColumn();
	
	public int getRow();
	
	public int getCenter();
	
	public Seat[] cloneSeats();
}
