package com.simpedb.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.function.Supplier;

import com.simpedb.core.config.SimpleDBConfig;
import com.simpedb.core.constant.ErrorCodes;
import com.simpedb.core.constant.SimpleDBConstants;
import com.simpedb.core.exceptions.SimpleDBException;

public class DriverConnectionStrategy implements Supplier<Connection> {
	
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
	
}
