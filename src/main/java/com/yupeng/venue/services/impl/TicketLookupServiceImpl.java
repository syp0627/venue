package com.yupeng.venue.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yupeng.venue.beans.Venue;
import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.services.TicketLookupService;
import com.yupeng.venue.utils.SeatsLookupHelper;

@Service
public class TicketLookupServiceImpl implements TicketLookupService {

	@Autowired
	private Venue venue;

	@Autowired
	private SeatsLookupHelper ticketLookupHelper;

	@Override
	public int numSeatsAvailable() {
		return venue.numSeatsAvailable();
	}

	@Override
	public List<Seat> findSeats(int numSeats) {
		return ticketLookupHelper.findSeats(venue, numSeats);
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public void setTicketLookupHelper(SeatsLookupHelper ticketLookupHelper) {
		this.ticketLookupHelper = ticketLookupHelper;
	}

	@Override
	public void refreshCache() {
		venue.loadSeats();
	}

}
