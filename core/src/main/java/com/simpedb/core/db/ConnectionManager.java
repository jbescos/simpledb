package com.simpedb.core.db;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simpedb.core.config.SimpleDBConfig;
import com.simpedb.core.constant.ErrorCodes;
import com.simpedb.core.constant.SimpleDBConstants;
import com.simpedb.core.exceptions.SimpleDBException;

public final class ConnectionManager {
	
	private static final Logger log = LoggerFactory.getLogger(ConnectionManager.class);
	private static final DBStrategy supplier;
	
	static {
		String jndi = SimpleDBConfig.getProperty(SimpleDBConstants.JDBC_JNDI);
		String driver = SimpleDBConfig.getProperty(SimpleDBConstants.JDBC_DRIVER);

		// TODO add DataSourceConnectionStrategy
		if (jndi != null) {
			supplier = new JndiConnectionStrategy(jndi);
			
		} else if (driver != null) {
			supplier = new DriverConnectionStrategy(driver);
		} else {
			throw new SimpleDBException(ErrorCodes.CONFIG_MISSING_JNDI_OR_DRIVER_ERROR);
		}
		log.debug("The connection provider will obtain connections from {}", supplier.getClass().getName());
		
		String flywayScripts = SimpleDBConfig.getProperty(SimpleDBConstants.FLYWAY_SCHEMA_MIGRATION);
		if (flywayScripts != null) {
			log.info("Running flyway scripts in {}", flywayScripts);
			supplier.runFlyway(flywayScripts);
		}
		
	}
	
	private ConnectionManager() {}

	public static Connection get() {
		return supplier.get();
	}
	
}
