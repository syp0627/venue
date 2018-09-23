package com.yupeng.venue.models;

import java.sql.Timestamp;
import java.util.List;

import com.yupeng.venue.enitities.Seat;

public class SeatHold {

	private int seatHodeId;
	private String customerEmail;
	private Timestamp expired;
	private List<Seat> seats;

	public Timestamp getExpired() {
		return expired;
	}

	public void setExpired(Timestamp expired) {
		this.expired = expired;
	}

	public int getSeatHodeId() {
		return seatHodeId;
	}

	public void setSeatHodeId(int seatHodeId) {
		this.seatHodeId = seatHodeId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

}
