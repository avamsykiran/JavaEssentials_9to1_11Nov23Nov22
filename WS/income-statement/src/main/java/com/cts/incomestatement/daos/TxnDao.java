package com.cts.incomestatement.daos;

import java.util.List;

import com.cts.incomestatement.models.Txn;

public interface TxnDao {
	List<Txn> findAll();
	Txn findById(long txnId);
	Txn save(Txn txn);
	boolean existsById(long txnId);
	void deleteById(long txnId);
}
