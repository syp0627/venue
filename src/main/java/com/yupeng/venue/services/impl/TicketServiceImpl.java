package com.yupeng.venue.services.impl;

import java.util.concurrent.locks.StampedLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yupeng.venue.models.SeatHold;
import com.yupeng.venue.services.TicketHoldService;
import com.yupeng.venue.services.TicketLookupService;
import com.yupeng.venue.services.TicketReserveService;
import com.yupeng.venue.services.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	StampedLock lock = new StampedLock();

	@Autowired
	private TicketLookupService ticketLookupService;

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
		SeatHold seatHold = null;
		while (seatHold == null) {
			if (numSeatsAvailable() < numSeats)
				return null;
			seatHold = ticketHoldService.holdSeats(numSeats, customerEmail);
		}
		return seatHold;
	}

	@Override
	public String reverveSeats(int seatHoldId, String customerEmail) {
		SeatHold seatHold = ticketHoldService.getSeatHold(seatHoldId, customerEmail);
		if (seatHold == null)
			return null;
		return ticketReserveService.reserveSeats(seatHold);
	}

}
