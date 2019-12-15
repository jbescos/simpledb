package com.simpedb.core.constant;

public enum ErrorCodes {
	
	CONFIG_INITIALIZE_ERROR(1001, "Cannot load the initial config %s"),
	CONFIG_JNDI_ERROR(1002, "Cannot obtain from JNDI %s"),
	CONFIG_MISSING_PROPERTY_ERROR(1003, "Property %s is required"),
	CONFIG_MISSING_JNDI_OR_DRIVER_ERROR(1004, "One of these properties must exist: " + SimpleDBConstants.JDBC_JNDI + " or " + SimpleDBConstants.JDBC_DRIVER),
	DB_JNDI_CONNECTION_ERROR(1101, "Cannot obtain a connection from JNDI datasource %s"),
	DB_DRIVER_CONNECTION_ERROR(1102, "Cannot obtain a connection from SQL Driver %s to URL %s"),
	DB_DRIVER_CLASS_ERROR(1102, "Cannot instance the SQL Driver %s from empty constructor");

	private final int errorCode;
	private final String message;
	
	private ErrorCodes(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	};
	
	public static String getErrorString(ErrorCodes error, String ... args) {
		String errorMessage = String.format(error.message, args);
		return new StringBuilder("Error ").append(error.errorCode).append(": ").append(errorMessage).toString();
	}
	
}
