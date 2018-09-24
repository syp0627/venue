package com.yupeng.venue.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yupeng.venue.beans.Venue;
import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.models.SeatsStatus;
import com.yupeng.venue.services.SeatsLookupService;
import com.yupeng.venue.utils.SeatsLookupHelper;

@Service
public class SeatsLookupServiceImpl implements SeatsLookupService {

	@Autowired
	private Venue venue;

	@Autowired
	private SeatsLookupHelper seatsLookupHelper;

	@Override
	public int numSeatsAvailable() {
		return venue.numSeatsAvailable();
	}

	@Override
	public List<Seat> findSeats(int numSeats) {
		return seatsLookupHelper.findSeats(venue, numSeats);
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public void setTicketLookupHelper(SeatsLookupHelper ticketLookupHelper) {
		this.seatsLookupHelper = ticketLookupHelper;
	}

	@Override
	public void refreshCache() {
		venue.loadSeats();
	}

	@Override
	public SeatsStatus getSeatsStatus() {
		return new SeatsStatus(venue.getRow(), venue.getColumn(), Arrays.asList(venue.getSeats()));
	}

}
