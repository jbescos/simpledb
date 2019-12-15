package com.simpedb.core.config;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

import com.simpedb.core.constant.SimpleDBConstants;

public class SimpleDBConfigTest {

	@Test
	public void getJdbcProperties() {
		Properties properties = SimpleDBConfig.getTruncatedByKeyProperties(SimpleDBConstants.JDBC_ROOT);
		assertEquals("Properties: " + properties, "org.h2.Driver", properties.getProperty("driver"));
	}
	
}
