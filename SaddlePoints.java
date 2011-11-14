package saddlePoints;

import java.util.Date;
import java.util.Random;

/**
 * Creates a number of random arrays, and checks each array to see if it
 * contains a saddle point. Prints the arrays and the results.
 * 
 * @author Xin Li
 * @author Zhou Tan
 */
public class SaddlePoints {
	static Random randgen = new Random();
	Date date = new Date();

	/**
	 * @param args
	 *            Unused.
	 */
	public static void main(String[] args) {
		new SaddlePoints().run();
	}

	/**
	 * Creates arrays various sizes (including some 2x2 arrays and some larger),
	 * fills them with random values, and prints each array and information
	 * about it. Keeps generating arrays until it has printed at least one with
	 * and one without a saddle point.
	 */
	void run() {
		randgen.setSeed(date.getTime());

		boolean hasSaddlePoint = false;
		boolean hasNoSaddlePoint = false;
		int size = 2;
		int range;
		int[][] array;
		while ((hasSaddlePoint && hasNoSaddlePoint) == false) {
			range = randgen.nextInt(900) + 50;
			array = createRandomArray(size, size, -range, range);
			if (hasSaddlePoint(array)) {
				hasSaddlePoint = true;
			} else {
				hasNoSaddlePoint = true;
			}
			printArray(array);
			printArrayInfo(array);
			System.out.println();
			size++;
			if (size >= 10)
				size = 2;
		}
	}

	/**
	 * Prints the array.
	 * 
	 * @param array
	 *            The array to be printed.
	 */
	void printArray(int[][] array) {
		// calculate the cell width by finding the longest number
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				int temp = Math.abs(array[i][j]);
				if (temp > max)
					max = temp;
			}
		}
		String formatString = "%" + (" " + max).length() + "d ";

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++)
				System.out.printf(formatString, array[i][j]);
			System.out.println();
		}

	}

	/**
	 * Prints whether the given array has a saddle point, and if so, where it is
	 * (row and column) and what its value is. (If there are multiple saddle
	 * points, just prints the first.)
	 * 
	 * @param array
	 *            The array to be checked.
	 */
	void printArrayInfo(int[][] array) {
		boolean hasSaddlePoint = hasSaddlePoint(array);

		if (!hasSaddlePoint) {
			System.out.println("This array has no saddle point.");
		} else {
			int saddlePointRow = saddlePointRow(array);
			int saddlePointColumn = saddlePointColumn(array);
			System.out.println("This array has a saddle point of value "
					+ array[saddlePointRow][saddlePointColumn] + " at row "
					+ saddlePointRow + " column " + saddlePointColumn);
		}
	}

	/**
	 * Creates and returns an array of the given size and fills it with random
	 * values in the specified range.
	 * 
	 * @param numberOfRows
	 *            The number of rows desired.
	 * @param numberOfColumns
	 *            The number of columns desired.
	 * @param minValue
	 *            The smallest number allowable in the array.
	 * @param maxValue
	 *            The largest number allowable in the array.
	 * @return
	 */
	int[][] createRandomArray(int numberOfRows, int numberOfColumns,
			int minValue, int maxValue) {
		int[][] array = new int[numberOfRows][numberOfColumns];
		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns; j++) {
				array[i][j] = (randgen.nextInt(maxValue - minValue + 1))
						+ minValue;
			}
		}

		return array;
	}

	/**
	 * Finds the largest value in an array of integers.
	 * 
	 * @param array
	 *            The array to be searched.
	 * @return The largest value in the array.
	 */
	int largest(int[] array) {
		int max = Integer.MIN_VALUE;
		for (int n : array) {
			if (n > max)
				max = n;
		}
		return max;
	}

	/**
	 * Finds the smallest value in an array of integers.
	 * 
	 * @param array
	 *            The array to be searched.
	 * @return The smallest value in the array.
	 */
	int smallest(int[] array) {
		int min = Integer.MAX_VALUE;
		for (int n : array) {
			if (n < min)
				min = n;
		}
		return min;
	}

	/**
	 * Returns an array containing the largest values in each column of the
	 * given array.
	 * 
	 * @param array
	 *            The array to be searched.
	 * @return An array of the largest values in each column.
	 */
	int[] largestValues(int[][] array) {
		int[] result = new int[array[0].length];
		for (int i = 0; i < array[0].length; i++) {
			int[] temp = new int[array.length];
			for (int j = 0; j < array.length; j++) {
				temp[j] = array[j][i];
			}
			result[i] = largest(temp);
		}
		return result;
	}

	/**
	 * Returns an array containing the smallest values in each row of the given
	 * array.
	 * 
	 * @param array
	 *            The array to be searched.
	 * @return An array of the smallest values in each row.
	 */
	int[] smallestValues(int[][] array) {
		int[] result = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = smallest(array[i]);
		}
		return result;
	}

	/**
	 * Returns true if the given array has a saddle point, and false if it does
	 * not.
	 * 
	 * @param array
	 *            The array to be checked.
	 * @return True if the array has a saddle point, else false.
	 */
	boolean hasSaddlePoint(int[][] array) {
		if (-1 != saddlePointRow(array))
			return true;
		return false;
	}

	/**
	 * Given an array that is known to have a saddle point, returns the number
	 * of a row containing a saddle point. If more than one row contains a
	 * saddle point, the first such row will be returned.
	 * 
	 * @param array
	 *            An array containing one or more saddle points.
	 * @return The lowest-numbered row containing a saddle point.
	 */
	int saddlePointRow(int[][] array) {
		int[] row = smallestValues(array);
		int[] column = largestValues(array);
		for (int i = 0; i < row.length; i++)
			for (int j = 0; j < column.length; j++)
				if (row[i] == column[j])
					return i;
		return -1;
	}

	/**
	 * Given an array that is known to have a saddle point, returns the number
	 * of a column containing a saddle point. If more than one column contains a
	 * saddle point, the first such column will be returned.
	 * 
	 * @param array
	 *            An array containing one or more saddle points.
	 * @return The lowest-numbered column containing a saddle point.
	 */

	int saddlePointColumn(int[][] array) {
		int[] row = smallestValues(array);
		int[] column = largestValues(array);
		for (int i = 0; i < row.length; i++)
			for (int j = 0; j < column.length; j++)
				if (row[i] == column[j])
					return j;
		return -1;
	}
}
