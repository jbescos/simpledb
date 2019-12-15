package com.simpedb.core.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class ConnectionTest {

	@Test
	public void connection() throws SQLException {
		Connection connection = ConnectionProvider.get();
		connection.close();
	}
	
}
