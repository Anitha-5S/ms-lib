package com.c2.lc.lib.exceptions;

public class DataFormatException extends Exception {

	private static final long serialVersionUID = -2474094377467730094L;
	private static final String INVALID_DATA_FORMAT_MESSAGE = " : Invalid data format!";

	public DataFormatException(String arg) {
		super(arg + INVALID_DATA_FORMAT_MESSAGE);
	}
	public DataFormatException(Long arg) {
		super(arg + INVALID_DATA_FORMAT_MESSAGE);
	}
	public DataFormatException(Short arg) {
		super(arg + INVALID_DATA_FORMAT_MESSAGE);
	}
	public DataFormatException(Integer arg) {
		super(arg + INVALID_DATA_FORMAT_MESSAGE);
	}
	public DataFormatException(Double arg) {
		super(arg + INVALID_DATA_FORMAT_MESSAGE);
	}

}
