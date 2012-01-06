package com.scireum.open.nucleus.incidents;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a compact error description which can be handled by instances of
 * {@link IncidentProcessor}.
 */
public class Incident {

	protected String name;
	protected String signature;
	protected String message;
	private String stacktrace;
	private String exceptionStacktrace = "";
	private Throwable exception;

	/**
	 * Creates a new Incident and pre-fills the local stacktrace.
	 */
	public Incident(String name, String message, Throwable exception,
			String signature) {
		super();
		this.name = name;
		this.message = message;
		this.exception = exception;
		List<StackTraceElement> stack = new ArrayList<StackTraceElement>(
				Arrays.asList(Thread.currentThread().getStackTrace()));
		int stackoffset = 3;
		while (!stack.isEmpty() && (stackoffset > 0)) {
			stack.remove(0);
			stackoffset--;
		}
		StringBuilder builder = new StringBuilder();
		for (StackTraceElement element : stack) {
			builder.append(element.getClassName());
			builder.append(".");
			builder.append(element.getMethodName());
			builder.append(" (");
			builder.append(element.getFileName());
			builder.append(":");
			builder.append(element.getLineNumber());
			builder.append(")");
			builder.append("\n");
		}
		stacktrace = builder.toString();
		if (exception != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			exception.printStackTrace(pw);
			this.exceptionStacktrace = sw.toString();
			pw.close();
		}
		this.signature = signature;
		if (signature == null) {
			this.signature = name;
		}
	}

	/**
	 * Returns the error message in the current users locale.
	 */
	public String getMessage() {
		return message;
	}

	public String getStacktrace() {
		return stacktrace;
	}

	public String getSignature() {
		return signature;
	}

	public String getExceptionStacktrace() {
		return exceptionStacktrace;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getType());
		builder.append(" \n---------------------------------------------\n");
		builder.append(getMessage());
		builder.append("\n---------------------------------------------\n");
		builder.append("Location:\n");
		builder.append(getSignature());
		builder.append("\n---------------------------------------------\n");
		return builder.toString();
	}

	public String getType() {
		return name;
	}

	public Throwable getException() {
		return exception;
	}
}
