package com.yupeng.venue.models;

import java.util.List;

public class SeatHold {

	private int seatHodeId;
	private String customerEmail;
	private long expired;
	private List<Integer> seatIndexs;

	public SeatHold(int seatHodeId, String customerEmail, long expired, List<Integer> seatIndexs) {
		this.seatHodeId = seatHodeId;
		this.customerEmail = customerEmail;
		this.expired = expired;
		this.seatIndexs = seatIndexs;
	}

	public long getExpired() {
		return expired;
	}

	public void setExpired(long expired) {
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

	public List<Integer> getSeatIndexs() {
		return seatIndexs;
	}

	public void setSeatIndexs(List<Integer> seatIndexs) {
		this.seatIndexs = seatIndexs;
	}

}
