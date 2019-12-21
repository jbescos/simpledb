package com.simpedb.core.db;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ConnectionTest {

	@Test
	public void connection() throws SQLException {
		List<Integer> records = new ArrayList<>();
		try(Connection connection = ConnectionManager.get(); PreparedStatement ps = connection.prepareStatement("select * from QUESTIONNAIRE_DESCRIPTION")) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				records.add(rs.getInt("QUESTIONNAIRE_ID"));
			}
		}
		assertTrue("Records " + records, records.contains(1));
	}
	
}
