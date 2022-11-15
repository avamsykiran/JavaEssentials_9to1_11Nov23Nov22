package com.cts.incomestatement.uis;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

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

	public StatementUI() {
		this.kbin = new Scanner(System.in);
		this.txnService = new TxnServiceImpl();
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
		System.out.print("Start Date (yyyy-MM-dd): ");
		LocalDate start = LocalDate.parse(kbin.next());
		System.out.print("End Date (yyyy-MM-dd): ");
		LocalDate end = LocalDate.parse(kbin.next());

		try {
			Statement stmt = txnService.getStatement(start, end);
			
			if (stmt.getTxns().isEmpty()) {
				System.out.println("No Transaction in the given period");
			} else {
				stmt.getTxns().stream().forEach(System.out::println);
				System.out.println("Total Credit: " + stmt.getTotalCredit());
				System.out.println("Total Debit: " + stmt.getTotalDebit());
				System.out.println("Balance: " + stmt.getStatementBalance());
			}
			
		} catch (DataOperationFailedException e) {
			System.out.println(e.getMessage());
		}

	}

	private void doDelete() {
		System.out.print("Transaction Id: ");
		long txnId = kbin.nextLong();
		try {
			txnService.deleteById(txnId);
			System.out.println("Transaction Deleted!");
		} catch (DataOperationFailedException | TxnNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	private void doAdd() {
		Txn txn = new Txn();

		System.out.print("Header: ");
		txn.setHeader(kbin.next());
		System.out.print("Amount: ");
		txn.setAmount(kbin.nextDouble());
		System.out.print("Type (CREDIT/DEBIT): ");
		txn.setType(TxnType.valueOf(kbin.next().toUpperCase()));
		System.out.print("Date (yyyy-MM-dd): ");
		txn.setTxnDate(LocalDate.parse(kbin.next()));

		try {
			txn = txnService.add(txn);
			System.out.println("Transaction Recorded with id:" + txn.getTxnId());
		} catch (DataOperationFailedException e) {
			System.out.println(e.getMessage());
		}

	}
}
