package com.scireum.open.nucleus.core;

import java.util.List;

import com.scireum.open.nucleus.Nucleus;

/**
 * Returns all object which were registered for a given class.
 */
public class Parts<P> {

	private List<P> objects;
	private Class<P> clazz;

	private Parts(Class<P> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Creates a new instance for the given class.
	 */
	public static <P> Parts<P> of(Class<P> clazz) {
		return new Parts<P>(clazz);
	}

	/**
	 * Returns a objects which were registered for the given class. This list is
	 * once fetched and kept in a local cache.
	 */
	public List<P> get() {
		if (objects == null) {
			objects = getUncached();
		}
		return objects;
	}

	/**
	 * Returns all objects without relying on the internal cache.
	 */
	public List<P> getUncached() {
		return Nucleus.findParts(clazz);
	}
}
