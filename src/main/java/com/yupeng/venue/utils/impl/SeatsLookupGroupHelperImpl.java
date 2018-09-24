package com.yupeng.venue.utils.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.springframework.stereotype.Component;

import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.utils.SeatsLookupGroupHelper;

@Component
public class SeatsLookupGroupHelperImpl implements SeatsLookupGroupHelper {

	@Override
	public List<Seat> findSeatsInGroup(Seat[] seats, int column, int[] priorityMap, int[] priorityIndex,
			int numSeats) {
		boolean[] visited = new boolean[seats.length];
		PriorityQueue<PrioritySet> priorityQueue = new PriorityQueue<PrioritySet>((a, b) -> {
			if (a.seats.size() != b.seats.size())
				return b.seats.size() - b.seats.size();
			return Double.compare(a.weight, b.weight);
		});
		for (int i = 0; i < priorityIndex.length; i++) {
			if (!visited[priorityIndex[i]] && seats[priorityIndex[i]].isAvalible()) {
				List<Seat> ret = new ArrayList<Seat>();
				Queue<Integer> queue = new LinkedList<>();
				queue.offer(priorityIndex[i]);
				visited[priorityIndex[i]] = true;
				while (!queue.isEmpty()) {
					int index = queue.poll();
					ret.add(seats[index]);
					if (ret.size() == numSeats)
						return ret;

					int rowIndex = index / column;
					int begin = rowIndex * column;
					int end = (rowIndex + 1) * column;
					int cursor = index - 1;
					while (cursor >= begin && !visited[cursor] && seats[cursor].isAvalible()) {
						visited[cursor] = true;
						queue.offer(cursor--);
					}

					cursor = index + 1;
					while (cursor < end && !visited[cursor] && seats[cursor].isAvalible()) {
						visited[cursor] = true;
						queue.offer(cursor++);
					}

					cursor = index - column;
					if (cursor >= 0 && !visited[cursor] && seats[cursor].isAvalible()) {
						visited[cursor] = true;
						queue.offer(cursor);
					}

					cursor = index + column;
					if (cursor < seats.length && !visited[cursor] && seats[cursor].isAvalible()) {
						visited[cursor] = true;
						queue.offer(cursor);
					}

				}

				priorityQueue.offer(new PrioritySet(ret,
						ret.stream().mapToInt(x -> priorityMap[x.getIndex()]).average().getAsDouble()));

			}
		}

		List<Seat> result = new ArrayList<Seat>();
		while (numSeats != 0 && !priorityQueue.isEmpty()) {
			List<Seat> tmp = priorityQueue.poll().seats;
			if (tmp.size() >= numSeats) {
				result.addAll(tmp.subList(0, numSeats));
				return result;
			}
			result.addAll(tmp);
			numSeats -= tmp.size();
		}

		return null;
	}

	class PrioritySet {
		double weight;
		List<Seat> seats;

		public PrioritySet(List<Seat> seats, double weight) {
			this.seats = seats;
			this.weight = weight;
		}
	}

}
