package com.scireum.open.nucleus.core;

import com.scireum.open.nucleus.Nucleus;
import com.scireum.open.nucleus.Nucleus.ClassLoadAction;

/**
 * Loads all classes wearing the @Register annotation.
 */
public class ServiceLoadAction implements ClassLoadAction {

	@Override
	public void handle(Class<?> clazz) throws Exception {
		if (clazz.isAnnotationPresent(Register.class)) {
			Object instance = clazz.newInstance();
			for (Class<?> marker : clazz.getAnnotation(Register.class)
					.classes()) {
				Nucleus.register(marker, instance);
			}
		}

	}

}
