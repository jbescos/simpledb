package com.simpedb.core.db;

import java.sql.Connection;
import java.util.function.Supplier;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.simpedb.core.config.SimpleDBConfig;
import com.simpedb.core.constant.ErrorCodes;
import com.simpedb.core.constant.SimpleDBConstants;
import com.simpedb.core.exceptions.SimpleDBException;

public final class ConnectionProvider {
	
	private static final Supplier<Connection> supplier;
	
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
	}
	
	private ConnectionProvider() {}

	public static Connection get() {
		return supplier.get();
	}
	
	
	
}
