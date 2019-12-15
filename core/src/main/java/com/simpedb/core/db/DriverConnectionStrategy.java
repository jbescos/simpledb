package com.simpedb.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.flywaydb.core.Flyway;

import com.simpedb.core.config.SimpleDBConfig;
import com.simpedb.core.constant.ErrorCodes;
import com.simpedb.core.constant.SimpleDBConstants;
import com.simpedb.core.exceptions.SimpleDBException;

public class DriverConnectionStrategy implements DBStrategy {
	
	private static final String USERNAME = "user";
	private static final String PASSWORD = "password";
	private final String driverName;
	private final String url;
	private final Properties dbProperties;

	public DriverConnectionStrategy (String driverName) {
		this.driverName = driverName;
		this.url = SimpleDBConfig.getMandatory(SimpleDBConstants.JDBC_URL);
		this.dbProperties = SimpleDBConfig.getTruncatedByKeyProperties(SimpleDBConstants.JDBC_ROOT);
		try {
			Class.forName(driverName);
		} catch (ReflectiveOperationException e) {
			throw new SimpleDBException(ErrorCodes.DB_DRIVER_CLASS_ERROR, e, driverName);
		}
		
	}

	@Override
	public Connection get() {
		try {
			return DriverManager.getConnection(url, dbProperties);
		} catch (SQLException e) {
			throw new SimpleDBException(ErrorCodes.DB_DRIVER_CONNECTION_ERROR, e, driverName, url);
		}
	}


	@Override
	public void runFlyway(String scriptsLocation) {
		Flyway.configure().dataSource(url, dbProperties.getProperty(USERNAME), dbProperties.getProperty(PASSWORD)).locations(scriptsLocation).load().migrate();
	}
	
}
