package com.yupeng.venue.services.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yupeng.venue.beans.Venue;
import com.yupeng.venue.config.AppConfig;
import com.yupeng.venue.services.TicketHoldService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class TicketHoldServiceImplTest {

	@Autowired
	private TicketHoldService ticketHoldService;

	@Autowired
	private Venue venue;

	@Test
	public void testEmptyAvaliableSeat() throws InterruptedException {
		int totalSeatsNum = venue.getSeats().length;
		assertTrue(venue.numSeatsAvailable() == totalSeatsNum);
		ticketHoldService.setHoldExpiredDuration(3);
		ticketHoldService.holdSeats(4, "aaa@mail.com");
		Thread.sleep(20);
		assertTrue(venue.numSeatsAvailable() == totalSeatsNum - 4);
		Thread.sleep(4000);
		assertTrue(venue.numSeatsAvailable() == totalSeatsNum);
	}

}
