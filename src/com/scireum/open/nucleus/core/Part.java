package com.scireum.open.nucleus.core;

import com.scireum.open.nucleus.Nucleus;

/**
 * Provides access to a part registered for a given type. The part is initially
 * fetched from the model and then cached locally.
 */
public class Part<P> {

	private P object;
	private Class<P> clazz;
	private boolean loaded;

	private Part(Class<P> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Creates a new part which queries for the given class.
	 */
	public static <P> Part<P> of(Class<P> clazz) {
		return new Part<P>(clazz);
	}

	/**
	 * Returns the first object which was registered for the given class.
	 */
	public P get() {
		if (!loaded) {
			object = Nucleus.findPart(clazz);
			loaded = true;
		}
		return object;
	}
}
