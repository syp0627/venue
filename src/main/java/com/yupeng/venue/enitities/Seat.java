package com.yupeng.venue.enitities;

import com.yupeng.venue.enums.SeatStatus;

public class Seat implements Cloneable{
	private int index;
	private SeatStatus status;

	public Seat(int index) {
		this.index = index;
		this.status = SeatStatus.AVAILABLE;
	}
	
	public Seat(int index, SeatStatus status) {
		this.index = index;
		this.status = status;
	}


	public int getIndex() {
		return index;
	}

	public SeatStatus getStatus() {
		return status;
	}

	public void setStatus(SeatStatus status) {
		this.status = status;
	}

	public boolean isAvalible() {
		return this.status == SeatStatus.AVAILABLE;
	}
	
	@Override
	public Seat clone() {
	    return new Seat(this.index, this.status);
	}

}
