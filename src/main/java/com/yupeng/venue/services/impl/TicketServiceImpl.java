package com.yupeng.venue.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yupeng.venue.exception.TicketsNotAvailableException;
import com.yupeng.venue.models.SeatHold;
import com.yupeng.venue.services.TicketHoldService;
import com.yupeng.venue.services.TicketLookupService;
import com.yupeng.venue.services.TicketReserveService;
import com.yupeng.venue.services.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

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
			int seatAvailble = numSeatsAvailable();
			if (seatAvailble < numSeats)
				throw new TicketsNotAvailableException(String
						.format("Number of Seats not avaliable, request : %d, available : %d", numSeats, seatAvailble));
			seatHold = ticketHoldService.holdSeats(numSeats, customerEmail);
		}
		return seatHold;
	}

	@Override
	public String reverveSeats(int seatHoldId, String customerEmail) {
		return ticketReserveService.reserveSeats(seatHoldId, customerEmail);
	}

}
