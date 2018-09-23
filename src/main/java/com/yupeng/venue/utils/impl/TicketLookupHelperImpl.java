package com.yupeng.venue.utils.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yupeng.venue.beans.Venue;
import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.utils.TicketLookupGroupHelper;
import com.yupeng.venue.utils.TicketLookupHelper;
import com.yupeng.venue.utils.TicketLookupOneLineHelper;

@Component
public class TicketLookupHelperImpl implements TicketLookupHelper {

	@Autowired
	private TicketLookupOneLineHelper ticketLookupOneLineHelper;

	@Autowired
	private TicketLookupGroupHelper ticketLookupGroupHelper;

	@Override
	public List<Seat> findTickets(Venue venue, int numSeats) {
		Seat[] seats = venue.cloneSeats();
		List<Seat> ret = ticketLookupOneLineHelper.findTicketsInOneLine(seats, venue.getColumn(),
				venue.getPriorityIndex(), numSeats);
		if (ret != null)
			return ret;
		return ticketLookupGroupHelper.findTicketsInGroup(seats, venue.getColumn(), venue.getPriorityMap(),
				venue.getPriorityIndex(), numSeats);
	}

}
