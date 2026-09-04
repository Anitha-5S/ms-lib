package com.c2.lc.lib.properties;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseProperties {

    protected Properties prop;

    protected void init(String fileName, Logger logger) {
        InputStream is;
        try {
            this.prop = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            is = loader.getResourceAsStream(fileName);
            prop.load(is);
        } catch (IOException e) {
            logger.error(e.getMessage(), new Exception(e.getMessage()));
        }
    }

}
