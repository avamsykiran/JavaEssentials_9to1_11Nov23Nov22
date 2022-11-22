package com.cts.incomestatement.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.incomestatement.daos.TxnDao;
import com.cts.incomestatement.entities.Txn;
import com.cts.incomestatement.entities.TxnType;
import com.cts.incomestatement.exceptions.DataOperationFailedException;
import com.cts.incomestatement.exceptions.TxnNotFoundException;
import com.cts.incomestatement.models.Statement;

@Service
public class TxnServiceImpl implements TxnService {

	@Autowired
	private TxnDao txnDao;
	
	@Override
	public List<Txn> getAll() throws DataOperationFailedException {
		return txnDao.findAll();
	}

	@Override
	public Txn getById(long txnId) throws DataOperationFailedException, TxnNotFoundException {
		if (!txnDao.existsById(txnId)) {
			throw new TxnNotFoundException("Transaction with id " + txnId + " does not exists!");
		}
		return txnDao.findById(txnId).orElse(null);
	}

	@Override
	public Txn add(Txn txn) throws DataOperationFailedException {
		return txnDao.save(txn);
	}

	@Override
	public Txn update(Txn txn) throws DataOperationFailedException, TxnNotFoundException {
		if(!txnDao.existsById(txn.getTxnId())) {
			throw new TxnNotFoundException("Transaction with id "+txn.getTxnId()+" does not exists!");
		}
		return txnDao.save(txn);
	}

	@Override
	public void deleteById(long txnId) throws DataOperationFailedException, TxnNotFoundException {
		if(!txnDao.existsById(txnId)) {
			throw new TxnNotFoundException("Transaction with id "+txnId+" does not exists!");
		}
		txnDao.deleteById(txnId);
	}

	private double accumilate(List<Txn> txns,TxnType type ) {
		double sum =0;
		
		if(!txns.isEmpty()) {
			sum = txns.stream().filter(t -> t.getType()==type).mapToDouble(Txn::getAmount).sum();
		}
		
		return sum;
	}
	
	@Override
	public Statement getStatement(LocalDate start, LocalDate end) throws DataOperationFailedException {
		List<Txn> txns = txnDao.findAllBetween(start, end);
		double totalCredit=accumilate(txns, TxnType.CREDIT);
		double totalDebit=accumilate(txns, TxnType.DEBIT);
		double statementBalance=totalCredit-totalDebit;
		Statement stmt = new Statement(start, end, txns, totalCredit, totalDebit, statementBalance);
		return stmt;
	}

}
