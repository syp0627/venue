package com.yupeng.venue.beans;

import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.jms.SeatsStatusUpdateJmsMessage;

public interface Venue {

	public Seat[] getSeats();
	
	public void loadSeats();

	public int[] getPriorityMap();

	public int[] getPriorityIndex();

	public int numSeatsAvailable();
	
	public int getColumn();
	
	public int getRow();
	
	public int getCenter();
	
	public Seat[] cloneSeats();
	
	public void updateSeatStatus(SeatsStatusUpdateJmsMessage message);
}
