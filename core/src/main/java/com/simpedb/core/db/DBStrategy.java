package com.simpedb.core.db;

import java.sql.Connection;
import java.util.function.Supplier;

public interface DBStrategy extends Supplier<Connection> {

	void runFlyway(String scriptsLocation);
	
}
