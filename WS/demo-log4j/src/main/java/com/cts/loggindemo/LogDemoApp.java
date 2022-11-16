package com.cts.loggindemo;

import org.apache.log4j.Logger;

public class LogDemoApp {

	public static void main(String[] args) {
		
		System.out.println("logging demo");
		Logger rootLogger = Logger.getRootLogger();
		
		rootLogger.info("going on a date");
		rootLogger.warn("ensure sufficient wallet balance");
		rootLogger.debug("confirm the time and place to meet");
		rootLogger.trace("keep track of your spendings");
		rootLogger.error("if wallet runs out of balance");
		rootLogger.fatal("you forgot her name");
		
		System.out.println("logging demo done");
	}

}
