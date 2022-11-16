package com.cts.incomestatement.uis;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.cts.incomestatement.exceptions.DataOperationFailedException;
import com.cts.incomestatement.exceptions.TxnNotFoundException;
import com.cts.incomestatement.models.Statement;
import com.cts.incomestatement.models.Txn;
import com.cts.incomestatement.models.TxnType;
import com.cts.incomestatement.services.TxnService;
import com.cts.incomestatement.services.TxnServiceImpl;

public class StatementUI {

	private Scanner kbin;
	private TxnService txnService;
	private Logger logger;

	public StatementUI() {
		this.kbin = new Scanner(System.in);
		this.txnService = new TxnServiceImpl();
		this.logger=Logger.getLogger(this.getClass());
	}

	public void run() {
		System.out.println("Income Statement Application");
		System.out.println("===================================================");

		Menu menu = null;
		String commands = Arrays.stream(Menu.values()).map(m -> m.toString()).reduce((m1, m2) -> m1 + "/" + m2)
				.orElse("");

		while (menu != Menu.QUIT) {
			System.out.print(commands + "> ");
			String cmd = kbin.next();
			menu = Menu.valueOf(cmd.toUpperCase());
			logger.info("Menu displayed and choice accepted");
			logger.debug(menu + " is the choice");
			switch (menu) {
			case ADD:
				doAdd();
				break;
			case DELETE:
				doDelete();
				break;
			case STATEMENT:
				doStatement();
				break;
			case QUIT:
				System.out.println("App Terminated!");
				break;
			}
		}
	}

	private void doStatement() {
		logger.info("Statement display being attempted");
		
		System.out.print("Start Date (yyyy-MM-dd): ");
		LocalDate start = LocalDate.parse(kbin.next());
		System.out.print("End Date (yyyy-MM-dd): ");
		LocalDate end = LocalDate.parse(kbin.next());

		logger.debug(start + "," + end + "are start and end dates");
		
		try {
			Statement stmt = txnService.getStatement(start, end);
			
			if (stmt.getTxns().isEmpty()) {
				logger.info("transactions are empty");
				System.out.println("No Transaction in the given period");
			} else {
				logger.info("statement being displayed");
				stmt.getTxns().stream().forEach(System.out::println);
				System.out.println("Total Credit: " + stmt.getTotalCredit());
				System.out.println("Total Debit: " + stmt.getTotalDebit());
				System.out.println("Balance: " + stmt.getStatementBalance());
			}
			
		} catch (DataOperationFailedException e) {
			logger.error(e);
			System.out.println(e.getMessage());
		}
	}

	private void doDelete() {
		logger.info("doDelete attempting");
		System.out.print("Transaction Id: ");
		long txnId = kbin.nextLong();
		logger.info(txnId + " to be deleted");
		try {
			txnService.deleteById(txnId);
			System.out.println("Transaction Deleted!");
			logger.info("txn deleted");
		} catch (DataOperationFailedException | TxnNotFoundException e) {
			logger.error(e);
			System.out.println(e.getMessage());
		}
	}

	private void doAdd() {
		logger.info("doAdd attempting");
		Txn txn = new Txn();

		System.out.print("Header: ");
		txn.setHeader(kbin.next());
		System.out.print("Amount: ");
		txn.setAmount(kbin.nextDouble());
		System.out.print("Type (CREDIT/DEBIT): ");
		txn.setType(TxnType.valueOf(kbin.next().toUpperCase()));
		System.out.print("Date (yyyy-MM-dd): ");
		txn.setTxnDate(LocalDate.parse(kbin.next()));

		logger.debug(txn);
		
		try {
			txn = txnService.add(txn);
			System.out.println("Transaction Recorded with id:" + txn.getTxnId());
			logger.debug(txn);
			logger.info("txn added");
		} catch (DataOperationFailedException e) {
			logger.error(e);
			System.out.println(e.getMessage());
		}

	}
}
