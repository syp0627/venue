package com.yupeng.venue.jms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yupeng.venue.enums.SeatStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatsStatusUpdateJmsMessage {

	private List<Integer> seatIndexs;
	private SeatStatus status;

	@JsonCreator
	public SeatsStatusUpdateJmsMessage(@JsonProperty("seatIndexs") List<Integer> seatIndexs,
			@JsonProperty("status") SeatStatus status) {
		this.seatIndexs = seatIndexs;
		this.status = status;
	}

	public List<Integer> getSeatIndexs() {
		return seatIndexs;
	}

	public void setSeatIndexs(List<Integer> seatIndexs) {
		this.seatIndexs = seatIndexs;
	}

	public SeatStatus getStatus() {
		return status;
	}

	public void setStatus(SeatStatus status) {
		this.status = status;
	}

}
