package com.simpedb.core.config;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simpedb.core.constant.ErrorCodes;
import com.simpedb.core.constant.SimpleDBConstants;
import com.simpedb.core.exceptions.SimpleDBException;
import com.simpedb.core.utils.FileUtils;

public final class SimpleDBConfig {
	
	private final static Logger log = LoggerFactory.getLogger(SimpleDBConfig.class);
	private final static Properties config;
	
	static {
		String configFile = System.getProperty(SimpleDBConstants.ARG_CONFIG_PROPERTY);
		if (configFile == null) {
			log.debug("No {} was specified", SimpleDBConstants.ARG_CONFIG_PROPERTY);
			configFile = SimpleDBConstants.DEFAULT_CONFIG_FILE;
		}
		log.info("Loading property file {}", configFile);
		try {
			config = FileUtils.loadPropertiesFromFile(configFile);
		} catch (IOException e) {
			throw new SimpleDBException(ErrorCodes.CONFIG_INITIALIZE_ERROR, e, configFile);
		}
	}
	
	private SimpleDBConfig() {}
	
	public static String getMandatory(String key) {
		String value = getProperty(key);
		if (value == null) {
			throw new SimpleDBException(ErrorCodes.CONFIG_MISSING_PROPERTY_ERROR, key);
		}
		return value;
	}
	
	public static String getProperty(String key) {
		return config.getProperty(key);
	}
	
	public static Properties getTruncatedByKeyProperties(String startsKey) {
		Properties properties = new Properties();
		for (Entry<Object, Object> property : config.entrySet()) {
			String key = property.getKey().toString();
			if (key.startsWith(startsKey)) {
				String truncated = key.substring(startsKey.length() - 1, key.length());
				properties.put(truncated, property.getValue());
			}
		}
		return properties;
	}
	
}
