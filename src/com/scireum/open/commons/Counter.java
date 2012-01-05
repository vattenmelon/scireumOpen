package com.scireum.open.commons;

import java.util.concurrent.TimeUnit;

/**
 * Represents a counter for statistical use.
 */
public class Counter {
	/**
	 * When was this counter started or last reseted?
	 */
	private volatile long startTimeMillis = -1;
	private volatile long count = 0;

	/**
	 * Increments the counter by one.
	 */
	public void inc() {
		if (startTimeMillis < 0) {
			reset();
		}
		if (count < Long.MAX_VALUE - 10) {
			count++;
		}
	}

	public double getAvgPer(TimeUnit unit) {
		return (count) / getDuration(unit);
	}

	public long getCount() {
		return count;
	}

	public long getDuration(TimeUnit unit) {
		long delta = System.currentTimeMillis() - startTimeMillis;
		return TimeUnit.MILLISECONDS.convert(delta, unit);
	}

	public void reset() {
		startTimeMillis = System.currentTimeMillis();
		count = 0;
	}
}
