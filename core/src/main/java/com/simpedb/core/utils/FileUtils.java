package com.simpedb.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {

	private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
	private static final String CLASSPATH_PROTOCOL = "classpath";
	private static final String FILE_PROTOCOL = "file";
	
	public static Properties loadPropertiesFromFile(String file) throws IOException {
		String[] pair = file.split(":");
		if (pair.length != 2) {
			throw new IllegalArgumentException(file + " has not the correct format: {protocol}:{path}");
		}
		if (CLASSPATH_PROTOCOL.equalsIgnoreCase(pair[0])) {
			try(InputStream input = FileUtils.class.getResourceAsStream(pair[1])){
				Properties prop = new Properties();
				prop.load(input);
				return prop;
			}
		} else if (FILE_PROTOCOL.equalsIgnoreCase(pair[0])) {
			File initialFile = new File(pair[1]);
			if (initialFile.exists()) {
				try (InputStream input = new FileInputStream(initialFile)) {
					Properties prop = new Properties();
					prop.load(input);
					return prop;
				}
			} else {
				throw new IllegalArgumentException("File " + file + " doesn't exist");
			}
		} else {
			throw new IllegalArgumentException("Protocol " + pair[0] + " is not known. Known protocols are: " + Arrays.asList(CLASSPATH_PROTOCOL, FILE_PROTOCOL));
		}
	}
	
}
