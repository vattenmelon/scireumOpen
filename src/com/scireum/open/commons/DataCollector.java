package com.scireum.open.commons;

import java.util.Collection;
import java.util.List;

/**
 * Instead of making methods return a {@link List} one can pass them a collector
 * to collect result items.
 * 
 * This eases the implementation code and gives more flexibility when handling
 * result lists.
 */
public interface DataCollector<T> {

	/**
	 * Adds an entity to the result set.
	 */
	void add(T entity);

	/**
	 * Adds a collection of entities to the result set.
	 */
	void add(Collection<? extends T> entities);
}
