package com.c2.lc.lib.properties;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyHandler {
	private static Logger logger = Logger.getLogger(PropertyHandler.class);

	private Properties prop = null;
	private static PropertyHandler handler;

	public PropertyHandler() {
		init();
	}

	private void init() {
		InputStream is = null;
		try {
			prop = new Properties();
			is = this.getClass().getResourceAsStream("app.properties"); 
			prop.load(is);
		} catch (IOException e) {
			logger.error(e.getMessage(), new Exception(e.getMessage()));
		}
	}

	private static PropertyHandler getInstance() {
		if (handler == null)
			handler = new PropertyHandler();

		return handler;
	}

	public static String getPropertyValue(String key) {
		return getInstance().prop.getProperty(key);
	}
}
