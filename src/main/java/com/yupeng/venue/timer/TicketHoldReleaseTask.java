package com.yupeng.venue.timer;

import java.util.TimerTask;

import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.yupeng.venue.enums.SeatStatus;
import com.yupeng.venue.jms.SeatsStatusUpdateJmsMessage;
import com.yupeng.venue.models.SeatHold;
import com.yupeng.venue.repositories.VenueRepositry;

@Component
@Scope("prototype")
public class TicketHoldReleaseTask extends TimerTask {

	private JmsTemplate jmsTemplate;

	private VenueRepositry venueRepositry;

	private SeatHold seatHold;

	public TicketHoldReleaseTask(SeatHold seatHold, JmsTemplate jmsTemplate, VenueRepositry venueRepositry) {
		this.jmsTemplate = jmsTemplate;
		this.venueRepositry = venueRepositry;
		this.seatHold = seatHold;
	}

	@Override
	public void run() {
		venueRepositry.releaseHoldSeats(seatHold.getSeatIndexs());
		jmsTemplate.convertAndSend("seatsUpdate",
				new SeatsStatusUpdateJmsMessage(seatHold.getSeatIndexs(), SeatStatus.AVAILABLE));

	}

}
