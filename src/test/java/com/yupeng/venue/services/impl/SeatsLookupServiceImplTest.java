package com.yupeng.venue.services.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.yupeng.venue.beans.impl.VenueImpl;
import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.enums.SeatStatus;
import com.yupeng.venue.repositories.VenueRepositry;
import com.yupeng.venue.repositories.memory.MemoryVenueRepositry;

@RunWith(PowerMockRunner.class)
public class SeatsLookupServiceImplTest {

	private SeatsLookupServiceImpl ticketLookupService = new SeatsLookupServiceImpl();
	private VenueImpl venue = new VenueImpl();
	private VenueRepositry venueRepositry = mock(MemoryVenueRepositry.class);

	@Before
	public void setup() throws Exception {
		venue.setVenueRepositry(venueRepositry);

		Seat[][] seats = new Seat[9][32];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 32; j++) {
				seats[i][j] = new Seat(i * 32 + j);
			}
		}
		when(venueRepositry.getSeats()).thenReturn(seats);
		venue.init();

		ticketLookupService.setVenue(venue);

	}

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
