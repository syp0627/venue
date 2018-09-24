package com.yupeng.venue.repositories;

import java.util.List;

public interface TikcetReserveRepository {
	public String save(String customerEmail, List<Integer> seatsIndex);
}
