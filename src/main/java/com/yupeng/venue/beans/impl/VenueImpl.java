package com.yupeng.venue.beans.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yupeng.venue.beans.Venue;
import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.enums.SeatStatus;
import com.yupeng.venue.repositories.VenueRepositry;

@Component
public class VenueImpl implements Venue {

	@Autowired
	private VenueRepositry venueRepositry;

	private ReadWriteLock lock = new ReentrantReadWriteLock();

	protected Seat[] seats;
	protected int column;
	protected int row;
	protected int[] priorityMap;
	protected int[] priorityIndex;

	@Override
	public Seat[] getSeats() {
		return seats;
	}

	@Override
	public int[] getPriorityMap() {
		return priorityMap;
	}

	@Override
	public int[] getPriorityIndex() {
		return priorityIndex;
	}

	@Override
	public int getColumn() {
		return column;
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getCenter() {
		return row / 2 * column + column / 2;
	}

	@Override
	public int numSeatsAvailable() {
		return (int) Stream.of(seats).flatMap(Stream::of).filter(s -> s.getStatus() == SeatStatus.AVAILABLE).count();
	}

	@PostConstruct
	protected void init() {
		initSeats();
		initPriority();
	}

	private void initPriority() {
		int index = 0, weight = 0;
		Queue<Integer> queue = new LinkedList<>();
		queue.add(getCenter());
		while (!queue.isEmpty()) {
			int size = queue.size();
			weight++;
			while (size-- > 0) {
				Integer i = queue.poll();
				if (priorityMap[i] != 0)
					continue;
				priorityIndex[index++] = i;
				priorityMap[i] = weight;
				
				int rowIndex = i / column;
				int begin =  rowIndex * column;
				int end = (rowIndex + 1) * column;
				int move = 1;
				while (move < 3 && i - move >= begin && priorityMap[i - move] == 0) {
					queue.offer(i - move++);
				}
				move = 1;
				while (move < 3 && i + move < end && priorityMap[i + move] == 0) {
					queue.offer(i + move++);
				}
				move = 1;
				while (move < 4 && i + move * column < seats.length && priorityMap[i + move * column] == 0) {
					queue.offer(i + move++ * column);
				}
				move = 1;
				if (i - column >= 0 && priorityMap[i - column] == 0) {
					queue.offer(i - column);
				}
			}
		}

	}

	private void initSeats() {
		Seat[][] oriSeats = venueRepositry.getSeats();
		this.seats = Arrays.stream(oriSeats).flatMap(Arrays::stream).toArray(Seat[]::new);
		this.column = oriSeats[0].length;
		this.row = oriSeats.length;
		this.priorityMap = new int[row * column];
		this.priorityIndex = new int[row * column];
	}

	@Override
	public Seat[] cloneSeats() {
		Seat[] cloneSeats = new Seat[row * column];
		lock.readLock().lock();
		try {
			for (int i = 0; i < seats.length; i++) {
				cloneSeats[i] = this.seats[i].clone();
			}
		} finally {
			lock.readLock().unlock();
		}
		return cloneSeats;
	}

}
