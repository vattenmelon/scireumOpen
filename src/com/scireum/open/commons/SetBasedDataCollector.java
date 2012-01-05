package com.scireum.open.commons;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Implements {@link DataCollector} buy using a {@link List}.
 */
public class SetBasedDataCollector<T> implements DataCollector<T> {

	private Set<T> data = new LinkedHashSet<T>();

	@Override
	public void add(T entity) {
		data.add(entity);
	}

	@Override
	public void add(Collection<? extends T> entities) {
		data.addAll(entities);
	}

	public Set<T> getData() {
		return data;
	}

}
