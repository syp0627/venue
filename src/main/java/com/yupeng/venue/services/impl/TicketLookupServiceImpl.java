package com.yupeng.venue.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yupeng.venue.beans.impl.VenueImpl;
import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.services.TicketLookupService;
import com.yupeng.venue.utils.TicketLookupHelper;

@Service
public class TicketLookupServiceImpl implements TicketLookupService {

	@Autowired
	private VenueImpl venue;

	@Autowired
	private TicketLookupHelper ticketLookupHelper;

	@Override
	public int numSeatsAvailable() {
		return venue.numSeatsAvailable();
	}

	@Override
	public List<Seat> findSeats(int numSeats) {
		if (numSeatsAvailable() < numSeats)
			return null;
		return ticketLookupHelper.findTickets(venue, numSeats);
	}

}
