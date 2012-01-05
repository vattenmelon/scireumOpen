package com.scireum.open.commons;

/**
 * Represents an average value over a given set of values.
 */
public class Average {

	private long count = 0;
	private long[] values = new long[100];
	private int index = 0;
	private int filled = 0;

	/**
	 * Adds value to the set of values on which the average is based. If the sum
	 * of all values is > Long.MAX_VALUE or the count of all values is >
	 * Long.Max_VALUE, the value is ignored.
	 */
	public void addValue(long value) {
		synchronized (values) {
			values[index++] = value;
			if (index >= values.length - 1) {
				index = 0;
			}
			if (index > filled) {
				filled = index;
			}
		}
		if (count >= Long.MAX_VALUE - 1) {
			count = 0;
		}
		count++;
	}

	public double getAvg() {
		if (filled == 0) {
			return 0.0D;
		}
		double result = 0.0d;
		for (int i = 0; i <= filled; i++) {
			result += values[i];
		}
		return result / (double) filled;
	}

	public long getCount() {
		return count;
	}
}
