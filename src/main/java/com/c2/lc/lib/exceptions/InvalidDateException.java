package com.c2.lc.lib.exceptions;

public class InvalidDateException extends Exception {

	private static final long serialVersionUID = -6476384852798420885L;

	public InvalidDateException() {
		super();
	}
	
	public InvalidDateException(String string) {
		super(string);
	}

	public InvalidDateException(String value, String string) {
		super("'" + value + "' - " + string);
	}
}
