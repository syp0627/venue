package com.yupeng.venue.utils.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Component;

import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.utils.TicketLookupOneLineHelper;

@Component
public class TicketLookupOneLineHelperImpl implements TicketLookupOneLineHelper {

	@Override
	public List<Seat> findTicketsInOneLine(Seat[] seats, int column, int[] priorityIndex, int numSeats) {
		boolean[] visited = new boolean[seats.length];
		for (int i = 0; i < priorityIndex.length; i++) {
			if (!visited[priorityIndex[i]] && seats[priorityIndex[i]].isAvalible()) {
				List<Seat> ret = new ArrayList<Seat>();
				Queue<Integer> queue = new LinkedList<>();
				queue.offer(priorityIndex[i]);
				int rowIndex = priorityIndex[i] / column;
				int begin =  rowIndex * column;
				int end = (rowIndex + 1) * column;
				while (!queue.isEmpty()) {
					int index = queue.poll();
					ret.add(seats[index]);
					if (ret.size() == numSeats)
						return ret;
					visited[index] = true;
					if (index - 1 >= begin && !visited[index - 1] && seats[index - 1].isAvalible()) {
						queue.offer(index - 1);
					}
					if (index + 1 < end && !visited[index + 1] && seats[index + 1].isAvalible()) {
						queue.offer(index + 1);
					}
				}
			}
		}
		return null;
	}

}
