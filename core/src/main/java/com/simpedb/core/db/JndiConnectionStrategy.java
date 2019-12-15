package com.simpedb.core.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;

import com.simpedb.core.constant.ErrorCodes;
import com.simpedb.core.exceptions.SimpleDBException;

public class JndiConnectionStrategy implements DBStrategy{

	private final DataSource dataSource;
	private final String jndi;
	
	public JndiConnectionStrategy(String jndi) {
		try {
			InitialContext context = new InitialContext();
			this.dataSource = (DataSource) context.lookup(jndi);
			this.jndi = jndi;
		} catch (NamingException e) {
			throw new SimpleDBException(ErrorCodes.CONFIG_JNDI_ERROR, e, jndi);
		}
	}
	
	@Override
	public Connection get() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new SimpleDBException(ErrorCodes.DB_JNDI_CONNECTION_ERROR, e, jndi);
		}
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	@Override
	public void runFlyway(String scriptsLocation) {
		Flyway.configure().dataSource(dataSource).locations(scriptsLocation).load().migrate();
	}

}
