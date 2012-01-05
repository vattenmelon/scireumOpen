package com.scireum.open.commons;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates unique ids for each call of "next". Once that almost Long.MAX_VALUE
 * ids where generated, the internal counter is reset and ids are re-used.
 * Therefore these ids are not meant to be persisted, since there is no
 * guarantee that they are unique for ever, but for a long time.
 */
public class AtomicIdGenerator {
	private final AtomicLong gen = new AtomicLong();

	public long next() {
		long next = gen.incrementAndGet();
		if (next > Long.MAX_VALUE - 10) {
			// If we have an overflow, we get a real lock, check if another
			// thread was faster, and if not, we reset the counter.
			synchronized (gen) {
				if (gen.get() > Long.MAX_VALUE - 10) {
					gen.set(0l);
				}
			}
		}
		return next;
	}

	public String nextString() {
		return String.valueOf(next());
	}
}
