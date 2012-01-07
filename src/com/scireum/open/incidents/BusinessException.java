package com.scireum.open.incidents;

/**
 * This is the main exception with is used by scireum software. It indicates,
 * that the error was already handled (via a Incident or kernelPanic) and that
 * the exception will contain an appropriate error message.
 * 
 * @author aha
 * 
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1180433275280350911L;

	/**
	 * Default constructor which takes an I18n message and the root exception
	 */
	public BusinessException(String message, Throwable root) {
		super(message, root);
	}

	/**
	 * Used for unhandled exceptions in unprotected services
	 */
	public BusinessException(Throwable root) {
		super(root);
	}

	/**
	 * Default constructor which takes an I18n message.
	 */
	public BusinessException(String message) {
		super(message);
	}

}
