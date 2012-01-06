package com.scireum.open.nucleus.timer;

/**
 * Parts implementing this interface will be invoked every minute.
 */
public interface EveryMinute {
	/**
	 * Called every time the timer interval is fired.
	 */
	void runTimer() throws Exception;
}
