package com.yupeng.venue.beans.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.yupeng.venue.enitities.Seat;
import com.yupeng.venue.enums.SeatStatus;
import com.yupeng.venue.repositories.VenueRepositry;
import com.yupeng.venue.repositories.memory.MemoryVenueRepositry;

@RunWith(PowerMockRunner.class)
public class VenueImplTest {

	private VenueImpl venue = new VenueImpl();
	private VenueRepositry venueRepositry = mock(MemoryVenueRepositry.class);

	private int[] targetPriority = new int[] { 13, 13, 12, 12, 11, 11, 10, 10, 9, 9, 8, 8, 7, 7, 6, 6, 5, 6, 6, 7, 7, 8,
			8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 12, 12, 11, 11, 10, 10, 9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 4, 5, 5, 6, 6, 7,
			7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 11, 11, 10, 10, 9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 4, 4, 5, 5, 6, 6,
			7, 7, 8, 8, 9, 9, 10, 10, 11, 10, 10, 9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 3, 3, 4, 4, 5, 5, 6, 6,
			7, 7, 8, 8, 9, 9, 10, 9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7,
			8, 8, 9, 10, 10, 9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10,
			10, 10, 9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 10,
			9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 11, 11, 10, 10,
			9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11 };

	private int[] targetIndex = new int[] { 144, 143, 142, 145, 146, 176, 208, 240, 112, 141, 175, 207, 239, 111, 140,
			174, 206, 238, 110, 147, 177, 209, 241, 113, 148, 178, 210, 242, 114, 272, 80, 139, 173, 205, 237, 109, 271,
			79, 138, 172, 204, 236, 108, 270, 78, 149, 179, 211, 243, 115, 273, 81, 150, 180, 212, 244, 116, 274, 82,
			48, 137, 171, 203, 235, 107, 269, 77, 47, 136, 170, 202, 234, 106, 268, 76, 46, 151, 181, 213, 245, 117,
			275, 83, 49, 152, 182, 214, 246, 118, 276, 84, 50, 16, 135, 169, 201, 233, 105, 267, 75, 45, 15, 134, 168,
			200, 232, 104, 266, 74, 44, 14, 153, 183, 215, 247, 119, 277, 85, 51, 17, 154, 184, 216, 248, 120, 278, 86,
			52, 18, 133, 167, 199, 231, 103, 265, 73, 43, 13, 132, 166, 198, 230, 102, 264, 72, 42, 12, 155, 185, 217,
			249, 121, 279, 87, 53, 19, 156, 186, 218, 250, 122, 280, 88, 54, 20, 131, 165, 197, 229, 101, 263, 71, 41,
			11, 130, 164, 196, 228, 100, 262, 70, 40, 10, 157, 187, 219, 251, 123, 281, 89, 55, 21, 158, 188, 220, 252,
			124, 282, 90, 56, 22, 129, 163, 195, 227, 99, 261, 69, 39, 9, 128, 162, 194, 226, 98, 260, 68, 38, 8, 159,
			189, 221, 253, 125, 283, 91, 57, 23, 190, 222, 254, 126, 284, 92, 58, 24, 161, 193, 225, 97, 259, 67, 37, 7,
			160, 192, 224, 96, 258, 66, 36, 6, 191, 223, 255, 127, 285, 93, 59, 25, 286, 94, 60, 26, 257, 65, 35, 5,
			256, 64, 34, 4, 287, 95, 61, 27, 62, 28, 33, 3, 32, 2, 63, 29, 30, 1, 0, 31 };

	@Before
	public void setup() throws Exception {
		venue.setVenueRepositry(venueRepositry);

		Seat[][] seats = new Seat[9][32];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 32; j++) {
				seats[i][j] = new Seat(i * 32 + j);
			}
		}
		when(venueRepositry.getSeats()).thenReturn(seats);
		venue.init();
	}

	@Test
	public void testVenueImplInit() {
		for (int i = 0; i < venue.getRow(); i++) {
			for (int j = 0; j < venue.getColumn(); j++) {
				assertTrue(
						venue.getPriorityMap()[i * venue.getColumn() + j] == targetPriority[i * venue.getColumn() + j]);
			}
		}
		for (int i = 0; i < venue.getPriorityIndex().length; i++) {
			assertTrue(venue.getPriorityIndex()[i] == targetIndex[i]);
		}

		assertTrue(venue.numSeatsAvailable() == venue.getPriorityIndex().length);
		venue.getSeats()[1].setStatus(SeatStatus.HOLD);
		venue.getSeats()[3 * venue.getColumn() + 4].setStatus(SeatStatus.RESERVED);
		assertTrue(venue.numSeatsAvailable() == venue.getPriorityIndex().length - 2);
	}

}
