package com.yupeng.venue.repositories.memory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Repository;

import com.yupeng.venue.models.SeatReserve;
import com.yupeng.venue.repositories.TikcetReserveRepository;

@Repository
public class MemoryTikcetReserveRepository implements TikcetReserveRepository {
	private final static int RECERVE_CODE_LENGTH = 6;

	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	//should clean after event finished.
	private Map<String, SeatReserve> reserveStorage;

	public MemoryTikcetReserveRepository() {
		reserveStorage = new HashMap<>();
	}

	@Override
	public String save(String customerEmail, List<Integer> seatsIndex) {
		lock.writeLock().lock();
		String reserveCode = "";
		try {
			reserveCode = RandomStringUtils.random(RECERVE_CODE_LENGTH, true, false);
			while (reserveStorage.containsKey(reserveCode)) {
				reserveCode = RandomStringUtils.random(RECERVE_CODE_LENGTH, true, false);
			}
			reserveStorage.put(reserveCode, new SeatReserve(reserveCode, customerEmail, seatsIndex));
		}finally {
			lock.writeLock().unlock();
		}
		
		return reserveCode;
	}

}
