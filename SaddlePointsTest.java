package saddlePoints;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Zhou Tan
 * @author Xin Li
 *
 */
public class SaddlePointsTest {
	SaddlePoints sp = new SaddlePoints(); // create an instance variable
	int[][] with, with2, with4;
	int[][] without, without2;
	@Before
	public void setUp() throws Exception {
		// If you use the same variables in multiple tests,
		// assign values to them here
		with = new int[][] {{-9, 12, -6},  //-9
				{ 7, 14,  5},              //5
				{10, -8,  3},              //-8
				{ 6, 17, -10 }};           //-10
		        //10,17,  5
		with2 = new int[][] {{7, 12, 5},  //5
                { 7, 14,  5},             //5
                {10, -8,  3},             //-8
                {10, 17, -10 }};          //-10
		       //10, 17,  5
		with4 = new int[][] { {  5,  7,  3,  6}, //3
							  { 10, 13, 10, 19}, //10
							  {  8,  4,  6, 15}, //4
							  {  6, 12,  4,  7}, //4
							  { 10, 14, 10, 13}};//10
							//  10  14  10  13
		without = new int[][] {	{ 1, -2,  3},
								{-6,  5, -4}, 
								{ 7, -8,  9}};
		without2 = new int[][] { {1, 4},
								 {3, 2}};
	}

	@Test
	public void testCreateRandomArray() {
		int numberOfRows = 30;
		int numberOfColumns = 70;
		int minValue = -100;
		int maxValue = 100;
		int[][] test1 = sp.createRandomArray(numberOfRows, numberOfColumns, minValue, maxValue);
		assertEquals(numberOfRows, test1.length); // see if the row number is right
		int firstVal = test1[0][0];
		boolean flag = true;
		for(int i = 0; i < test1.length; i++) {
			assertEquals(numberOfColumns, test1[i].length); // see if the col number is right
			for(int j = 0; j < test1[i].length; j++) {
				if(test1[i][j] != firstVal) {
					flag = false;
				}
				assertTrue(test1[i][j] <= maxValue && test1[i][j] >= minValue); // see if the number is in range
			}
		}
		assertFalse(flag); // if not all values in the array are the same, flag will be false
	}

	@Test
	public void testLargest() {
		assertEquals(10, sp.largest(new int[] {10, 9, 8, 7, 6, 5}));
		assertEquals(15,sp.largest(new int[] {10, 11, 15, -20, 0, -21}));
		assertEquals(15, sp.largest(new int[] {15, 11, 15, -20, 0, -21}));
		assertEquals(0,sp.largest(new int[] {-15, -11, -15, -20, 0, -21}));
		assertEquals(-11, sp.largest(new int[] {-15, -11, -15, -20, -11, -21}));
	}

	@Test
	public void testSmallest() {
	    assertEquals(5, sp.smallest(new int[] {10, 9, 8, 7, 6, 5}));
        assertEquals(-21, sp.smallest(new int[] {10, 11, 15, -20, 0, -21}));
        assertEquals(-21, sp.smallest(new int[] {15, 11, -21, -20, 0, -21}));
        assertEquals(0, sp.smallest(new int[] {15, 11, 15, 20, 0, 21}));
        assertEquals(11, sp.smallest(new int[] {15, 11, 15, 20, 11, 21}));
	}

	@Test
	public void testLargestValues() {
		assertArrayEquals(new int[] {10, 17, 5}, sp.largestValues(with));
		assertArrayEquals(new int[] {7, 5, 9}, sp.largestValues(without));
	}

	@Test
	public void testSmallestValues() {
		assertArrayEquals(new int[] {-9, 5, -8, -10}, sp.smallestValues(with));
		assertArrayEquals(new int[] {-2, -6, -8}, sp.smallestValues(without));
	}

	@Test
	public void testHasSaddlePoint() {
		assertTrue(sp.hasSaddlePoint(with));
		assertTrue(sp.hasSaddlePoint(with2));
		assertTrue(sp.hasSaddlePoint(with4));
		assertFalse(sp.hasSaddlePoint(without));
		assertFalse(sp.hasSaddlePoint(without2));
	}

	@Test
	public void testSaddlePointRow() {
		assertEquals(1, sp.saddlePointRow(with));
		assertEquals(0, sp.saddlePointRow(with2));
		assertEquals(1, sp.saddlePointRow(with4));
	}

	@Test
	public void testSaddlePointColumn() {
		assertEquals(2, sp.saddlePointColumn(with));
		assertEquals(2, sp.saddlePointColumn(with2));
		assertEquals(0, sp.saddlePointColumn(with4));
	}

}
