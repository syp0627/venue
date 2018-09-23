package com.yupeng.venue.repositories.memory;

import org.springframework.stereotype.Repository;

import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.repositories.VenueRepositry;

@Repository
public class MemoryVenueRepositry implements VenueRepositry {

	// venue info should generate through DB or env, hard code for this task.
	public final static int ROW = 9;
	public final static int COLUMN = 32;

	@Override
	public Seat[][] getSeats() {
		Seat[][] seats = new Seat[ROW][COLUMN];
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COLUMN; j++) {
				seats[i][j] = new Seat(i * COLUMN + j);
			}
		}
		return seats;
	}

}
