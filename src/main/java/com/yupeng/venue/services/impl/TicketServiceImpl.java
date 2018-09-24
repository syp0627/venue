package com.yupeng.venue.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yupeng.venue.models.SeatHold;
import com.yupeng.venue.services.TicketHoldService;
import com.yupeng.venue.services.SeatsLookupService;
import com.yupeng.venue.services.TicketReserveService;
import com.yupeng.venue.services.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private SeatsLookupService ticketLookupService;

	@Autowired
	private TicketHoldService ticketHoldService;

	@Autowired
	private TicketReserveService ticketReserveService;

	@Override
	public int numSeatsAvailable() {
		return ticketLookupService.numSeatsAvailable();
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		return ticketHoldService.holdSeats(numSeats, customerEmail);
	}

	@Override
	public String reverveSeats(int seatHoldId, String customerEmail) {
		return ticketReserveService.reserveSeats(seatHoldId, customerEmail);
	}

}
