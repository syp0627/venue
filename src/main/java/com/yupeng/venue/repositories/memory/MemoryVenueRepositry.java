package com.yupeng.venue.repositories.memory;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.stereotype.Repository;

import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.enums.SeatStatus;
import com.yupeng.venue.repositories.VenueRepositry;

@Repository
public class MemoryVenueRepositry implements VenueRepositry {

	// venue info should generate through DB or env, hard code for this task.
	public final static int ROW = 9;
	public final static int COLUMN = 32;
	private Seat[][] seats;

	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public MemoryVenueRepositry() {
		this.seats = new Seat[ROW][COLUMN];
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COLUMN; j++) {
				seats[i][j] = new Seat(i * COLUMN + j);
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
				if (!this.seats[seat.getIndex() / COLUMN][seat.getIndex() % COLUMN].isAvalible())
					return false;
			}
			for (Seat seat : seatList) {
				this.seats[seat.getIndex() / COLUMN][seat.getIndex() % COLUMN].setStatus(SeatStatus.HOLD);
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
				this.seats[index / COLUMN][index % COLUMN].setStatus(SeatStatus.AVAILABLE);
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
				this.seats[index / COLUMN][index % COLUMN].setStatus(SeatStatus.RESERVED);
			}

		} finally {
			lock.writeLock().unlock();
		}
	}

}
