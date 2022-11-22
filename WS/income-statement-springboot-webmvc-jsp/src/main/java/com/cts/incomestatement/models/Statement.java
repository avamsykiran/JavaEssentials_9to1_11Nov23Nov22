package com.cts.incomestatement.models;

import java.time.LocalDate;
import java.util.List;

import com.cts.incomestatement.entities.Txn;

public class Statement {

	private LocalDate startDate;
	private LocalDate endDate;
	private List<Txn> txns;
	private double totalCredit;
	private double totalDebit;
	private double statementBalance;
	
	public Statement() {
		
	}
	public Statement(LocalDate startDate, LocalDate endDate, List<Txn> txns, double totalCredit, double totalDebit,
			double statementBalance) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.txns = txns;
		this.totalCredit = totalCredit;
		this.totalDebit = totalDebit;
		this.statementBalance = statementBalance;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public List<Txn> getTxns() {
		return txns;
	}
	public void setTxns(List<Txn> txns) {
		this.txns = txns;
	}
	public double getTotalCredit() {
		return totalCredit;
	}
	public void setTotalCredit(double totalCredit) {
		this.totalCredit = totalCredit;
	}
	public double getTotalDebit() {
		return totalDebit;
	}
	public void setTotalDebit(double totalDebit) {
		this.totalDebit = totalDebit;
	}
	public double getStatementBalance() {
		return statementBalance;
	}
	public void setStatementBalance(double statementBalance) {
		this.statementBalance = statementBalance;
	}
	
	
}
