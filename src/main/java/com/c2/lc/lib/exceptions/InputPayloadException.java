package com.c2.lc.lib.exceptions;

import java.util.ArrayList;
import java.util.List;

public class InputPayloadException extends Exception {

	private static final long serialVersionUID = -6082487009262342917L;
	private List<String> errorMessages = new ArrayList<>();
	
	public InputPayloadException(String errorMessage){
		super(errorMessage);
		errorMessages.add(errorMessage);
	}
	public InputPayloadException(String value, String errorMessage){
		errorMessages.add("'" + value + "' - " + errorMessage);
	}
	public InputPayloadException(List<String> errorMessages){
		super(errorMessages.get(0));
		this.setErrorMessages(errorMessages);
	}
	public List<String> getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
}
