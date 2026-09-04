package com.c2.lc.lib.properties;

import org.apache.log4j.Logger;

public class AppProperties extends BaseProperties {
	private static Logger logger = Logger.getLogger(AppProperties.class);

	private static AppProperties appProperties = null;

	public AppProperties() {
		this.init("app.properties", logger);
	}


	private static AppProperties getInstance() {
		if (appProperties == null)
			appProperties = new AppProperties();
		return appProperties;
	}

	public static String getPropertyValue(String key) {
		return getInstance().prop.getProperty(key);
	}
}
