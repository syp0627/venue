package com.yupeng.venue.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.models.SeatHold;
import com.yupeng.venue.services.TicketHoldService;
import com.yupeng.venue.services.TicketLookupService;

@Service
public class TicketHoldServiceImpl implements TicketHoldService {

	@Autowired
	private TicketLookupService ticketLookupService;

	@Override
	public SeatHold holdSeats(int numSeats, String customerEmail) {
		List<Seat> seats = ticketLookupService.findSeats(numSeats);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeatHold getSeatHold(int seatHoldId, String customerEmail) {
		// TODO Auto-generated method stub
		return null;
	}

}
