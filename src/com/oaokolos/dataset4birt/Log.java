/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oaokolos.dataset4birt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rkievskiy
 */
public class Log {
	public static final int LOG_LEVEL_NONE		= 0;
	public static final int LOG_LEVEL_CRITICAL	= 1;
	public static final int LOG_LEVEL_ERROR		= 2;
	public static final int LOG_LEVEL_WARNING	= 4;
	public static final int LOG_LEVEL_INFO		= 8;
	public static final int LOG_LEVEL_DEBUG		= 16;
	public static final int LOG_LEVEL_ALL		= 31;
	
	private static final String NONE		= "NONE";
	private static final String CRITICAL	= "CRITICAL";
	private static final String ERROR		= "ERROR";
	private static final String WARNING		= "WARNING";
	private static final String INFO		= "INFO";
	private static final String DEBUG		= "DEBUG";
	private static final String ALL			= "ALL";
	private static final String UNKNOWN		= "UNKNOWN";
	
	private boolean needFile = true;
	private String fileName = null;
	private File file = null;
	private PrintWriter writer = null;
	private int logLevel = LOG_LEVEL_ALL;
	private String dateTimeFormat = "[yyyy-MM-dd HH:mm:ss]";
	
	public Log(String fileName, int logLevel) {
		this.logLevel = logLevel;
		
		if (fileName.equals("")) {
			this.needFile = false;
		} else {
			this.needFile = this.init(fileName);
		}
	}
	
	private boolean init(String fileName) {
		this.fileName = fileName;
		this.file = new File(this.fileName);
		try {
			if (!this.file.exists()) {
				FileWriter fw = new FileWriter(this.file, true);
				fw.write("");
				fw.close();
			}
			
			this.writer = new PrintWriter(this.file, "UTF-8");
		} catch (IOException ex) {
			Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex.getMessage());
			return false;
		}
		
		return true;
	}
	
	private String getTimeStr() {
		return ZonedDateTime.now().format(DateTimeFormatter.ofPattern(this.dateTimeFormat));
	}
	
	private void printLn(String message, int logLevel) {
		if ((this.logLevel & logLevel) == logLevel && this.needFile) {
			this.writer.println(this.getTimeStr() + " " + getLevelName(logLevel) + " " + message);
			this.writer.flush();
		}
		
		System.out.println(message);
	}
	
	public void critical(String message) {
		this.printLn(message, LOG_LEVEL_CRITICAL);
	}

	public void error(String message) {
		this.printLn(message, LOG_LEVEL_ERROR);
	}

	public void warning(String message) {
		this.printLn(message, LOG_LEVEL_WARNING);
	}

	public void info(String message) {
		this.printLn(message, LOG_LEVEL_INFO);
	}

	public void debug(String message) {
		this.printLn(message, LOG_LEVEL_DEBUG);
	}

	public static String getLevelName(int logLevel) {
		switch (logLevel) {
			case LOG_LEVEL_NONE:		return NONE;
			case LOG_LEVEL_CRITICAL:	return CRITICAL;
			case LOG_LEVEL_ERROR:		return ERROR;
			case LOG_LEVEL_WARNING:		return WARNING;
			case LOG_LEVEL_INFO:		return INFO;
			case LOG_LEVEL_DEBUG:		return DEBUG;
			case LOG_LEVEL_ALL:			return ALL;
			default:					return UNKNOWN;
		}
	}
}
