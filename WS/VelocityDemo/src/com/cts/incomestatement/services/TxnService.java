package com.cts.incomestatement.services;

import java.time.LocalDate;
import java.util.List;

import com.cts.incomestatement.exceptions.DataOperationFailedException;
import com.cts.incomestatement.exceptions.TxnNotFoundException;
import com.cts.incomestatement.models.Statement;
import com.cts.incomestatement.models.Txn;

public interface TxnService {
	List<Txn> getAll() throws DataOperationFailedException;
	Txn getById(long txnId) throws DataOperationFailedException,TxnNotFoundException;
	Txn add(Txn txn) throws DataOperationFailedException;
	Txn update(Txn txn) throws DataOperationFailedException,TxnNotFoundException;
	void deleteById(long txnId) throws DataOperationFailedException,TxnNotFoundException;
	Statement getStatement(LocalDate start,LocalDate end) throws DataOperationFailedException;
}
