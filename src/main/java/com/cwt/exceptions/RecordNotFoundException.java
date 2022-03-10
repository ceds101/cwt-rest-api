package com.cwt.exceptions;

public class RecordNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1597248306409472986L;

	public RecordNotFoundException(String exception) {
		super(exception);
	}
}
