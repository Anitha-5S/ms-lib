package com.c2.lc.lib.exceptions;

public class NotImplementedMethodException extends RuntimeException {

	private static final long serialVersionUID = 4626337703827619619L;
	
	public NotImplementedMethodException(Class<?> className, String methodName) {
		super("Method " + methodName + " of class " + className.getName() + " not implemented");
	}

}
