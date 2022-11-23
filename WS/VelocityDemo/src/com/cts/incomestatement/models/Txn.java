package com.cts.incomestatement.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Txn implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long txnId;
	private String header;
	private double amount;
	private TxnType type;
	private LocalDate txnDate;
	
	public Txn() {
		
	}

	public Txn(long txnId, String header, double amount, TxnType type, LocalDate txnDate) {
		super();
		this.txnId = txnId;
		this.header = header;
		this.amount = amount;
		this.type = type;
		this.txnDate = txnDate;
	}

	public long getTxnId() {
		return txnId;
	}

	public void setTxnId(long txnId) {
		this.txnId = txnId;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public TxnType getType() {
		return type;
	}

	public void setType(TxnType type) {
		this.type = type;
	}

	public LocalDate getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(LocalDate txnDate) {
		this.txnDate = txnDate;
	}

	@Override
	public String toString() {
		return "Txn [txnId=" + txnId + ", header=" + header + ", amount=" + amount + ", type=" + type + ", txnDate="
				+ txnDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + ((txnDate == null) ? 0 : txnDate.hashCode());
		result = prime * result + (int) (txnId ^ (txnId >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Txn other = (Txn) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		if (txnDate == null) {
			if (other.txnDate != null)
				return false;
		} else if (!txnDate.equals(other.txnDate))
			return false;
		if (txnId != other.txnId)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
}
