package com.scireum.open.commons;

import java.util.concurrent.TimeUnit;

/**
 * Used to measure the duration of arbitrary code pieces.
 */
public class Watch {

	private long startTime = 0L;

	/**
	 * Starts a new watch.
	 */
	public static Watch start() {
		return new Watch();
	}

	/**
	 * Creates a new watch.
	 */
	private Watch() {
		super();
		reset();
	}

	/**
	 * Resets the watch, so that elapsed will return the millis since this call.
	 */
	public void reset() {
		startTime = System.nanoTime();
	}

	/**
	 * Returns the amount of millis since the last call to reset or object
	 * instantiation.
	 */
	public long elapsed() {
		return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
	}

	/**
	 * Returns the formatted duration
	 */
	public String duration() {
		// TODO use intelligent time-formatter, like:
		// http://joda-time.sourceforge.net/api-release/org/joda/time/format/PeriodFormatter.html
		return elapsed() + "ms";
	}

	public String microDuration(boolean reset) {
		String result = String
				.valueOf(((double) System.nanoTime() - (double) startTime) / 1000d);
		if (reset) {
			reset();
		}
		return result;
	}

	/**
	 * Combines duration and reset...
	 */
	public String durationReset() {
		// TODO use intelligent time-formatter, like:
		// http://joda-time.sourceforge.net/api-release/org/joda/time/format/PeriodFormatter.html
		String result = elapsed() + "ms";
		reset();
		return result;
	}
}
