package com.cts.incomestatement;

import org.apache.log4j.Logger;

import com.cts.incomestatement.uis.StatementUI;

public class IncomeStatementApplication {

	public static void main(String[] args) {
		Logger rootLogger = Logger.getRootLogger();
		rootLogger.info("App Initiated");
		StatementUI ui = new StatementUI();
		ui.run();
		rootLogger.info("App Terminated");
	}

}
