package com.cts.incomestatement.services;

import java.time.LocalDate;
import java.util.List;

import com.cts.incomestatement.models.Statement;
import com.cts.incomestatement.models.Txn;

public interface TxnService {
	List<Txn> getAll();
	Txn getById(long txnId);
	Txn add(Txn txn);
	Txn update(Txn txn);
	void deleteById(long txnId);
	Statement getStatement(LocalDate start,LocalDate end);
}
