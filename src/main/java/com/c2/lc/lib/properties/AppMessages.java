package com.c2.lc.lib.properties;


import com.c2.lc.lib.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AppMessages {

	private static AppMessages appMessages = null;
	private Map<String, Properties> prop = new HashMap<>();

	private AppMessages() {
		init();
	}

	private void init() {
		InputStream is;
		Properties properties;
		ClassLoader loader;
		try {
			// load english
			properties = new Properties();
			loader = Thread.currentThread().getContextClassLoader();
			is = loader.getResourceAsStream(Constants.LANGUAGE_ENGLISH_MESSAGES_PROPERTIES_FILENAME);
			properties.load(is);
			this.prop.put(Constants.LANGUAGE_ENGLISH, properties) ;

			// load kannada
/*
			properties = new Properties();
			loader = Thread.currentThread().getContextClassLoader();
			is = loader.getResourceAsStream(Constants.LANGUAGE_KANNADA_MESSAGES_PROPERTIES_FILENAME);
			properties.load(is);
			this.prop.put(Constants.LANGUAGE_KANNADA, properties) ;
*/

		} catch (IOException e) {
			//logger.error(e.getMessage(), new Exception(e.getMessage()));
		}
	}

	private static AppMessages getInstance() {
		if (appMessages == null)
			appMessages = new AppMessages();

		return appMessages;
	}

	public static String getPropertyValue(String key) {
		String retMessage = getPropertyValue(key, Constants.LANGUAGE_ENGLISH);
		return retMessage == null ? key : retMessage;
	}
	
	public static String getPropertyValue(String key, String locale) {
		return getInstance().prop.get(locale).getProperty(key);
	}

}
