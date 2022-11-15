package com.cts.incomestatement.daos;

import java.time.LocalDate;
import java.util.List;

import com.cts.incomestatement.exceptions.DataOperationFailedException;
import com.cts.incomestatement.models.Txn;

public interface TxnDao {
	List<Txn> findAll() throws DataOperationFailedException;
	List<Txn> findAllBetween(LocalDate start,LocalDate end) throws DataOperationFailedException;
	Txn findById(long txnId) throws DataOperationFailedException;
	Txn save(Txn txn) throws DataOperationFailedException;
	boolean existsById(long txnId) throws DataOperationFailedException;
	void deleteById(long txnId) throws DataOperationFailedException;
}
