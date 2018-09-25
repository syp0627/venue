package com.yupeng.venue.services.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.jms.core.JmsTemplate;

import com.yupeng.venue.beans.impl.VenueImpl;
import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.enums.SeatStatus;
import com.yupeng.venue.exception.TicketsHoldExpiredException;
import com.yupeng.venue.exception.TicketsHoldInfoNotValidedException;
import com.yupeng.venue.exception.TicketsNotAvailableException;
import com.yupeng.venue.jms.SeatsStatusUpdateJmsMessage;
import com.yupeng.venue.models.SeatHold;
import com.yupeng.venue.repositories.TikcetHoldRepository;
import com.yupeng.venue.repositories.VenueRepositry;
import com.yupeng.venue.repositories.memory.MemoryTikcetHoldRepository;
import com.yupeng.venue.repositories.memory.MemoryVenueRepositry;
import com.yupeng.venue.utils.impl.SeatsLookupGroupHelperImpl;
import com.yupeng.venue.utils.impl.SeatsLookupHelperImpl;
import com.yupeng.venue.utils.impl.SeatsLookupOneLineHelperImpl;

@RunWith(PowerMockRunner.class)
public class TicketHoldServiceImplTest {

	private SeatsLookupHelperImpl ticketLookupHelper = new SeatsLookupHelperImpl();
	private VenueImpl venue = new VenueImpl();
	private VenueRepositry venueRepositry = mock(MemoryVenueRepositry.class);
	private TikcetHoldRepository tikcetHoldRepository = mock(MemoryTikcetHoldRepository.class);
	private SeatsLookupOneLineHelperImpl seatsLookupOneLineHelper = new SeatsLookupOneLineHelperImpl();
	private SeatsLookupGroupHelperImpl seatsLookupGroupHelper = new SeatsLookupGroupHelperImpl();
	private TicketHoldServiceImpl ticketHoldService = new TicketHoldServiceImpl();
	private SeatsLookupServiceImpl ticketLookupServiceImpl = new SeatsLookupServiceImpl();
	private JmsTemplate jmsTemplate = mock(JmsTemplate.class);

	@Before
	public void setup() throws Exception {
		ticketLookupHelper.setSeatsLookupGroupHelper(seatsLookupGroupHelper);
		ticketLookupHelper.setSeatsLookupOneLineHelper(seatsLookupOneLineHelper);
		venue.setVenueRepositry(venueRepositry);

		Seat[][] seats = new Seat[9][32];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 32; j++) {
				seats[i][j] = new Seat(i * 32 + j);
			}
		}
		when(venueRepositry.getSeats()).thenReturn(seats);
		when(venueRepositry.holdSeats(Mockito.any())).thenReturn(true);
		when(venueRepositry.releaseHoldSeats(Mockito.anyList())).thenReturn(true);
		when(tikcetHoldRepository.save(Mockito.any(SeatHold.class))).thenReturn(true);
		doNothing().when(jmsTemplate).convertAndSend(Mockito.anyString(),
				Mockito.any(SeatsStatusUpdateJmsMessage.class));
		venue.init();

		ticketLookupServiceImpl.setTicketLookupHelper(ticketLookupHelper);
		ticketLookupServiceImpl.setVenue(venue);

		ticketHoldService.setTicketLookupService(ticketLookupServiceImpl);
		ticketHoldService.setJmsTemplate(jmsTemplate);
		ticketHoldService.setTikcetHoldRepository(tikcetHoldRepository);
		ticketHoldService.setVenueRepositry(venueRepositry);
	}

	@Test
	public void testEmptyAvaliableSeat() {
		int totalSeatsNum = venue.getSeats().length;
		assertTrue(venue.numSeatsAvailable() == totalSeatsNum);
		ticketHoldService.setHoldExpiredDuration(1);
		SeatHold seatHold = ticketHoldService.holdSeats(4, "aaa@gm.com");
		venue.updateSeatStatus(new SeatsStatusUpdateJmsMessage(seatHold.getSeatIndexs(), SeatStatus.HOLD));
		assertTrue(venue.numSeatsAvailable() == totalSeatsNum - 4);

		venue.updateSeatStatus(new SeatsStatusUpdateJmsMessage(seatHold.getSeatIndexs(), SeatStatus.AVAILABLE));
		assertTrue(venue.numSeatsAvailable() == totalSeatsNum);
	}

	@Test(expected = TicketsNotAvailableException.class)
	public void testNoAvaliableSeat() {
		ticketHoldService.holdSeats(venue.numSeatsAvailable() + 1, "aaa@gm.com");
	}

	@Test(expected = TicketsHoldInfoNotValidedException.class)
	public void testGetUnavaliableSeatHold() {
		when(tikcetHoldRepository.get(Mockito.anyInt())).thenReturn(null);
		ticketHoldService.getHoldSeats(0, "bbb@gm.com");

	}

	@Test(expected = TicketsHoldInfoNotValidedException.class)
	public void testGetUnmatchEmailSeatHold() {
		SeatHold seatHold = new SeatHold(0, "aaa@gm.com", Calendar.getInstance().getTimeInMillis() + 1500, null);
		when(tikcetHoldRepository.get(Mockito.anyInt())).thenReturn(seatHold);
		ticketHoldService.getHoldSeats(0, "bbb@gm.com");
	}

	@Test(expected = TicketsHoldExpiredException.class)
	public void testGetExpiredSeatHold() {
		SeatHold seatHold = new SeatHold(0, "aaa@gm.com", Calendar.getInstance().getTimeInMillis() - 1500, null);
		when(tikcetHoldRepository.get(Mockito.anyInt())).thenReturn(seatHold);
		ticketHoldService.getHoldSeats(0, "aaa@gm.com");
	}

}
