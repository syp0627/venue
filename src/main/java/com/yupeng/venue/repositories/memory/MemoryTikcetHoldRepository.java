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
	public SeatHold get(int seatHoldId) {
		return seatHoldId >= this.seatholds.size() ? null : this.seatholds.get(seatHoldId);
	}

	@Override
	public boolean save(SeatHold seatHold) {
		lock.writeLock().lock();
		try {
			seatHold.setSeatHodeId(this.seatholds.size());
			this.seatholds.add(seatHold);
		} finally {
			lock.writeLock().unlock();
		}
		return true;
	}

}
