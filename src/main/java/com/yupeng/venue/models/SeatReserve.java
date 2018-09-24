package com.yupeng.venue.models;

import java.util.List;

public class SeatReserve {

	private String reserveCode;
	private String customerEmail;
	private List<Integer> seatIndexs;

	public SeatReserve(String reserveCode, String customerEmail, List<Integer> seatIndexs) {
		this.reserveCode = reserveCode;
		this.customerEmail = customerEmail;
		this.seatIndexs = seatIndexs;
	}

	public String getReserveCode() {
		return reserveCode;
	}

	public void setReserveCode(String reserveCode) {
		this.reserveCode = reserveCode;
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
