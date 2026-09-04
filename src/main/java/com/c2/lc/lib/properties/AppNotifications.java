package com.c2.lc.lib.properties;


import com.c2.lc.lib.utils.Constants;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AppNotifications {
	private static Logger logger = Logger.getLogger(AppNotifications.class);

	private static AppNotifications appNotifications = null;
	private Map<String, Properties> prop = new HashMap<>();

	private AppNotifications() {
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
			is = loader.getResourceAsStream(Constants.LANGUAGE_ENGLISH_NOTIFICATIONS_PROPERTIES_FILENAME);
			properties.load(is);
			this.prop.put(Constants.LANGUAGE_ENGLISH, properties) ;

			// load kannada
			properties = new Properties();
			loader = Thread.currentThread().getContextClassLoader();
			is = loader.getResourceAsStream(Constants.LANGUAGE_KANNADA_NOTIFICATIONS_PROPERTIES_FILENAME);
			properties.load(is);
			this.prop.put(Constants.LANGUAGE_KANNADA, properties) ;
	
		} catch (IOException e) {
			logger.error(e.getMessage(), new Exception(e.getMessage()));
		}
	}

	private static AppNotifications getInstance() {
		if (appNotifications == null)
			appNotifications = new AppNotifications();

		return appNotifications;
	}

	public static String getPropertyValue(String key) {
		return getPropertyValue(key, Constants.LANGUAGE_ENGLISH);
	}
	
	public static String getPropertyValue(String key, String locale) {
		return getInstance().prop.get(locale).getProperty(key);
	}

}
