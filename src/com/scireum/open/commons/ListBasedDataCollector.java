package com.scireum.open.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implements {@link DataCollector} buy using a {@link List}.
 */
public class ListBasedDataCollector<T> implements DataCollector<T> {

	private List<T> data = new ArrayList<T>();

	@Override
	public void add(T entity) {
		data.add(entity);
	}

	@Override
	public void add(Collection<? extends T> entities) {
		data.addAll(entities);
	}

	public List<T> getData() {
		return data;
	}

}
