package com.yupeng.venue.utils.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

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
public class SeatsLookupHelperImplTest {

	private SeatsLookupHelperImpl ticketLookupHelper = new SeatsLookupHelperImpl();
	private VenueImpl venue = new VenueImpl();
	private VenueRepositry venueRepositry = mock(MemoryVenueRepositry.class);
	private SeatsLookupOneLineHelperImpl seatsLookupOneLineHelper = new SeatsLookupOneLineHelperImpl();
	private SeatsLookupGroupHelperImpl seatsLookupGroupHelper = new SeatsLookupGroupHelperImpl();

	@Before
	public void setup() throws Exception {
		ticketLookupHelper.setSeatsLookupGroupHelper(seatsLookupGroupHelper);
		ticketLookupHelper.setSeatsLookupOneLineHelper(seatsLookupOneLineHelper);
		venue.setVenueRepositry(venueRepositry);

		Seat[][] seats = new Seat[MemoryVenueRepositry.ROW][MemoryVenueRepositry.COLUMN];
		for (int i = 0; i < MemoryVenueRepositry.ROW; i++) {
			for (int j = 0; j < MemoryVenueRepositry.COLUMN; j++) {
				seats[i][j] = new Seat(i * MemoryVenueRepositry.COLUMN + j);
			}
		}
		when(venueRepositry.getSeats()).thenReturn(seats);
		venue.init();
	}

	@Test
	public void getGetTicketNormalCase() {
		List<Seat> seats = ticketLookupHelper.findSeats(venue, 4);
		int column = venue.getColumn();
		assertTrue(seats.size() == 4);
		int center = venue.getCenter();
		venue.getSeats()[center].setStatus(SeatStatus.HOLD);
		venue.getSeats()[center - 1].setStatus(SeatStatus.HOLD);
		venue.getSeats()[center + 1].setStatus(SeatStatus.HOLD);
		venue.getSeats()[center - 2].setStatus(SeatStatus.HOLD);
		seats = ticketLookupHelper.findSeats(venue, 6);
		venue.getSeats()[center + 2].setStatus(SeatStatus.HOLD);
		venue.getSeats()[center + 3].setStatus(SeatStatus.HOLD);
		venue.getSeats()[center + 4].setStatus(SeatStatus.HOLD);
		venue.getSeats()[center + 5].setStatus(SeatStatus.HOLD);
		venue.getSeats()[center + 6].setStatus(SeatStatus.HOLD);
		venue.getSeats()[center + 7].setStatus(SeatStatus.HOLD);
		assertTrue(seats.size() == 6);
		seats = ticketLookupHelper.findSeats(venue, 8);
		assertTrue(seats.size() == 8);
		assertTrue(seats.get(0).getIndex() == center + column);
		assertTrue(seats.get(1).getIndex() == center + column - 1);
		assertTrue(seats.get(2).getIndex() == center + column + 1);
		assertTrue(seats.get(3).getIndex() == center + column - 2);
		assertTrue(seats.get(4).getIndex() == center + column + 2);
		assertTrue(seats.get(5).getIndex() == center + column - 3);
		assertTrue(seats.get(6).getIndex() == center + column + 3);
		assertTrue(seats.get(7).getIndex() == center + column - 4);
	}

	@Test
	public void getGetTicketCenterMatchCase() {
		Seat[] seats = venue.getSeats();
		for (Seat seat : venue.getSeats()) {
			seat.setStatus(SeatStatus.RESERVED);
		}

		seats[142].setStatus(SeatStatus.AVAILABLE);
		seats[143].setStatus(SeatStatus.AVAILABLE);
		seats[144].setStatus(SeatStatus.AVAILABLE);
		seats[146].setStatus(SeatStatus.AVAILABLE);

		seats[175].setStatus(SeatStatus.AVAILABLE);
		seats[176].setStatus(SeatStatus.AVAILABLE);
		seats[177].setStatus(SeatStatus.AVAILABLE);
		seats[174].setStatus(SeatStatus.AVAILABLE);

		List<Seat> seatsList = ticketLookupHelper.findSeats(venue, 4);

		assertTrue(seatsList.get(0).getIndex() == 176);
		assertTrue(seatsList.get(1).getIndex() == 175);
		assertTrue(seatsList.get(2).getIndex() == 177);
		assertTrue(seatsList.get(3).getIndex() == 174);

	}

	@Test
	public void getGetTicketCornerMatchCase() {
		Seat[] seats = venue.getSeats();
		int column = venue.getColumn();
		int row = seats.length / column;
		for (Seat seat : venue.getSeats()) {
			seat.setStatus(SeatStatus.RESERVED);
		}

		seats[0].setStatus(SeatStatus.AVAILABLE);
		seats[column].setStatus(SeatStatus.AVAILABLE);
		seats[(row - 1) * column].setStatus(SeatStatus.AVAILABLE);
		seats[seats.length - 1].setStatus(SeatStatus.AVAILABLE);

		List<Seat> seatsList = ticketLookupHelper.findSeats(venue, 4);

		assertTrue(seatsList.get(0).getIndex() == (row - 1) * column);
		assertTrue(seatsList.get(1).getIndex() == column);
		assertTrue(seatsList.get(2).getIndex() == 0);
		assertTrue(seatsList.get(3).getIndex() == seats.length - 1);

	}
}
