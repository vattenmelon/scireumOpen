package com.scireum.open.nucleus.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classes wearing this annotation will be instantiated using the no-args
 * constructor and registered for the listed classes.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface Register {
	/**
	 * Names the classes for which the created instance will be registered.
	 */
	Class<?>[] classes();
}
