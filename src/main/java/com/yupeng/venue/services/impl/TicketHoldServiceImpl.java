package com.yupeng.venue.services.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.enums.SeatStatus;
import com.yupeng.venue.exception.TicketsHoldExpiredException;
import com.yupeng.venue.exception.TicketsHoldFailedException;
import com.yupeng.venue.exception.TicketsHoldInfoNotValidedException;
import com.yupeng.venue.exception.TicketsNotAvailableException;
import com.yupeng.venue.jms.SeatsStatusUpdateJmsMessage;
import com.yupeng.venue.models.SeatHold;
import com.yupeng.venue.repositories.TikcetHoldRepository;
import com.yupeng.venue.repositories.VenueRepositry;
import com.yupeng.venue.services.TicketHoldService;
import com.yupeng.venue.services.TicketLookupService;
import com.yupeng.venue.timer.TicketHoldReleaseTask;

@Service
public class TicketHoldServiceImpl implements TicketHoldService {

	private int holdExpiredUnit = Calendar.SECOND;
	private int holdExpiredDuration = 30;

	private Map<Integer, Timer> timerStorage = new HashMap<Integer, Timer>();

	@Autowired
	private TicketLookupService ticketLookupService;

	@Autowired
	private VenueRepositry venueRepositry;

	@Autowired
	private TikcetHoldRepository tikcetHoldRepository;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public SeatHold holdSeats(int numSeats, String customerEmail) {
		SeatHold seatHold = null;
		while (seatHold == null) {
			int seatAvailble = ticketLookupService.numSeatsAvailable();
			if (seatAvailble < numSeats)
				throw new TicketsNotAvailableException(String
						.format("Number of Seats not avaliable, request : %d, available : %d", numSeats, seatAvailble));
			List<Seat> seats = ticketLookupService.findSeats(numSeats);
			if (venueRepositry.holdSeats(seats)) {
				seatHold = buildSeatHold(seats, customerEmail);

				// update Venue seats cache, this is just for simulate multiple servers,
				// consider all servers will receive update notice.
				jmsTemplate.convertAndSend("seatsUpdate",
						new SeatsStatusUpdateJmsMessage(seatHold.getSeatIndexs(), SeatStatus.HOLD));

				// release holding seats after expired
				addSeatHoldReleaseTimer(seatHold);
				return seatHold;
			} else {
				ticketLookupService.refreshCache();
			}
		}
		return seatHold;

	}

	protected SeatHold buildSeatHold(List<Seat> seats, String customerEmail) {
		List<Integer> seatsIndex = seats.stream().map(x -> x.getIndex()).collect(Collectors.toList());
		Calendar calendar = Calendar.getInstance();
		calendar.add(holdExpiredUnit, holdExpiredDuration);
		SeatHold seatHold = new SeatHold(customerEmail, calendar.getTimeInMillis(), seatsIndex);
		if (tikcetHoldRepository.save(seatHold)) {
			return seatHold;
		} else {
			throw new TicketsHoldFailedException(String.format("Ticket hold failed, please try again later."));
		}
	}

	protected void addSeatHoldReleaseTimer(SeatHold seatHold) {
		Timer timer = new Timer();
		timer.schedule(new TicketHoldReleaseTask(seatHold, jmsTemplate, venueRepositry), getHoldMilliscond());
		timerStorage.put(seatHold.getSeatHodeId(), timer);
	}

	@Override
	public SeatHold getHoldSeats(int seatHoldId, String customerEmail) {
		SeatHold seatHold = tikcetHoldRepository.get(seatHoldId);
		if (seatHold == null || !seatHold.getCustomerEmail().equals(customerEmail)) {
			throw new TicketsHoldInfoNotValidedException(String
					.format("Ticket Holding information is not valid: id: %d, email: %s ", seatHoldId, customerEmail));
		}

		if (seatHold.getExpired() < Calendar.getInstance().getTimeInMillis()) {
			throw new TicketsHoldExpiredException(
					String.format("Ticket Holding exipred: id: %d, email: %s ", seatHoldId, customerEmail));
		}

		timerStorage.get(seatHoldId).cancel();
		return seatHold;
	}

	@Override
	public void setHoldExpiredUnit(int holdExpiredUnit) {
		this.holdExpiredUnit = holdExpiredUnit;
	}

	@Override
	public void setHoldExpiredDuration(int holdExpiredDuration) {
		this.holdExpiredDuration = holdExpiredDuration;
	}

	@Override
	public long getHoldMilliscond() {
		try {
			switch (this.holdExpiredUnit) {
			case Calendar.SECOND:
				return this.holdExpiredDuration * 1000;
			case Calendar.MINUTE:
				return this.holdExpiredDuration * 60 * 1000;
			case Calendar.HOUR:
				return this.holdExpiredDuration * 60 * 60 * 1000;
			}
		} catch (Exception e) {
		}

		return 60 * 60 * 1000;
	}

	public void setTicketLookupService(TicketLookupService ticketLookupService) {
		this.ticketLookupService = ticketLookupService;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setVenueRepositry(VenueRepositry venueRepositry) {
		this.venueRepositry = venueRepositry;
	}

	public void setTikcetHoldRepository(TikcetHoldRepository tikcetHoldRepository) {
		this.tikcetHoldRepository = tikcetHoldRepository;
	}

}
