package com.yupeng.venue.services.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yupeng.venue.beans.Venue;
import com.yupeng.venue.config.AppConfig;
import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.enums.SeatStatus;
import com.yupeng.venue.services.TicketLookupService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class TicketLookupServiceImplTest {

	@Autowired
	private TicketLookupService ticketLookupService;

	@Autowired
	private Venue venue;

	@Test
	public void testEmptyAvaliableSeat() {
		for (Seat seat : venue.getSeats()) {
			seat.setStatus(SeatStatus.RESERVED);
		}
		assertTrue(ticketLookupService.numSeatsAvailable() == 0);
	}

	@Test
	public void testFullAvaliableSeat() {
		assertTrue(ticketLookupService.numSeatsAvailable() == venue.getSeats().length);
	}

	@Test
	public void testAvaliableSeat() {
		for (Seat seat : venue.getSeats()) {
			seat.setStatus(SeatStatus.RESERVED);
		}

		venue.getSeats()[venue.getCenter()].setStatus(SeatStatus.AVAILABLE);
		assertTrue(ticketLookupService.numSeatsAvailable() == 1);
	}
}
