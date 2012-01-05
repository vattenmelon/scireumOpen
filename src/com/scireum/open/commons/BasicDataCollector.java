package com.scireum.open.commons;

import java.util.Collection;

public abstract class BasicDataCollector<T> implements DataCollector<T> {

	@Override
	public void add(Collection<? extends T> entities) {
		for (T entity : entities) {
			add(entity);
		}
	}

}
