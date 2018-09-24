package com.yupeng.venue.utils.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yupeng.venue.beans.Venue;
import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.utils.SeatsLookupGroupHelper;
import com.yupeng.venue.utils.SeatsLookupHelper;
import com.yupeng.venue.utils.SeatsLookupOneLineHelper;

@Component
public class SeatsLookupHelperImpl implements SeatsLookupHelper {

	@Autowired
	private SeatsLookupOneLineHelper seatsLookupOneLineHelper;

	@Autowired
	private SeatsLookupGroupHelper seatsLookupGroupHelper;

	@Override
	public List<Seat> findSeats(Venue venue, int numSeats) {
		Seat[] seats = venue.cloneSeats();
		List<Seat> ret = seatsLookupOneLineHelper.findSeatsInOneLine(seats, venue.getColumn(), venue.getPriorityIndex(),
				numSeats);
		if (ret != null)
			return ret;
		return seatsLookupGroupHelper.findSeatsInGroup(seats, venue.getColumn(), venue.getPriorityMap(),
				venue.getPriorityIndex(), numSeats);
	}

	public void setSeatsLookupOneLineHelper(SeatsLookupOneLineHelper seatsLookupOneLineHelper) {
		this.seatsLookupOneLineHelper = seatsLookupOneLineHelper;
	}

	public void setSeatsLookupGroupHelper(SeatsLookupGroupHelper seatsLookupGroupHelper) {
		this.seatsLookupGroupHelper = seatsLookupGroupHelper;
	}

}
