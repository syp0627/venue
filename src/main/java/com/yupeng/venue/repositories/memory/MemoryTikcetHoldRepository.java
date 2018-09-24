package com.yupeng.venue.repositories.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.stereotype.Repository;

import com.yupeng.venue.models.SeatHold;
import com.yupeng.venue.repositories.TikcetHoldRepository;

@Repository
public class MemoryTikcetHoldRepository implements TikcetHoldRepository {

	private ReadWriteLock lock = new ReentrantReadWriteLock();

	private List<SeatHold> seatholds;

	public MemoryTikcetHoldRepository() {
		this.seatholds = new ArrayList<SeatHold>();
	}

	@Override
	public SeatHold save(String customerEmail, long expired, List<Integer> seatsIndex) {
		lock.writeLock().lock();
		SeatHold seatHold;
		try {
			int index = this.seatholds.size();
			seatHold = new SeatHold(index, customerEmail, expired, seatsIndex);
			this.seatholds.add(seatHold);
		} finally {
			lock.writeLock().unlock();
		}
		return seatHold;
	}

	@Override
	public SeatHold get(int seatHoldId) {
		return seatHoldId >= this.seatholds.size() ? null : this.seatholds.get(seatHoldId);
	}

}
