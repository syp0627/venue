package com.yupeng.venue.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.yupeng.venue.enums.SeatStatus;
import com.yupeng.venue.exception.TicketsReserveFailedException;
import com.yupeng.venue.jms.SeatsStatusUpdateJmsMessage;
import com.yupeng.venue.models.SeatHold;
import com.yupeng.venue.repositories.TikcetReserveRepository;
import com.yupeng.venue.repositories.VenueRepositry;
import com.yupeng.venue.services.TicketHoldService;
import com.yupeng.venue.services.TicketReserveService;

@Service
public class TicketReserveServiceImpl implements TicketReserveService {

	@Autowired
	private TicketHoldService ticketHoldService;

	@Autowired
	private TikcetReserveRepository tikcetReserveRepository;

	@Autowired
	private VenueRepositry venueRepositry;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		SeatHold seatHold = ticketHoldService.getHoldSeats(seatHoldId, customerEmail);
		String reserveCode = null;
		if (venueRepositry.releaseHoldSeats(seatHold.getSeatIndexs())) {
			reserveCode = tikcetReserveRepository.save(customerEmail, seatHold.getSeatIndexs());
			jmsTemplate.convertAndSend("seatsUpdate",
					new SeatsStatusUpdateJmsMessage(seatHold.getSeatIndexs(), SeatStatus.RESERVED));
		} else {
			throw new TicketsReserveFailedException(String.format("Ticket reserve failed, please try again later."));
		}

		return reserveCode;
	}

}
