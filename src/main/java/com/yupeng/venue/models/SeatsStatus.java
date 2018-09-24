package com.yupeng.venue.models;

import java.util.List;

import com.yupeng.venue.enitities.Seat;

public class SeatsStatus {

	private int row;
	private int column;
	private List<Seat> seats;
	
	public SeatsStatus(int row, int column, List<Seat> seats) {
		super();
		this.row = row;
		this.column = column;
		this.seats = seats;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	

}
