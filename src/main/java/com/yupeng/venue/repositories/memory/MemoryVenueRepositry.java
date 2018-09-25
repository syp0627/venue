package com.yupeng.venue.repositories.memory;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.enums.SeatStatus;
import com.yupeng.venue.repositories.VenueRepositry;

@Repository
public class MemoryVenueRepositry implements VenueRepositry {

	private int column = 32;

	private Seat[][] seats;

	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public MemoryVenueRepositry(@Value("${venue.seats.row}") int row, @Value("${venue.seats.column}") int column) {
		this.column = column;
		this.seats = new Seat[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				seats[i][j] = new Seat(i * column + j);
			}
		}
	}

	@Override
	public Seat[][] getSeats() {
		return this.seats;
	}

	@Override
	public boolean holdSeats(List<Seat> seatList) {
		lock.writeLock().lock();
		try {
			for (Seat seat : seatList) {
				if (!this.seats[seat.getIndex() / column][seat.getIndex() % column].isAvalible())
					return false;
			}
			for (Seat seat : seatList) {
				this.seats[seat.getIndex() / column][seat.getIndex() % column].setStatus(SeatStatus.HOLD);
			}

		} finally {
			lock.writeLock().unlock();
		}
		return true;
	}

	@Override
	public boolean releaseHoldSeats(List<Integer> seatList) {
		lock.writeLock().lock();
		try {
			for (int index : seatList) {
				this.seats[index / column][index % column].setStatus(SeatStatus.AVAILABLE);
			}
		} finally {
			lock.writeLock().unlock();
		}
		return true;
	}

	@Override
	public void reserveSeats(List<Integer> seatList) {
		lock.writeLock().lock();
		try {
			for (int index : seatList) {
				this.seats[index / column][index % column].setStatus(SeatStatus.RESERVED);
			}

		} finally {
			lock.writeLock().unlock();
		}
	}

}
