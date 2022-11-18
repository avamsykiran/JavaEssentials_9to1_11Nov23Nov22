package com.cts.incomestatement.services;

import java.time.LocalDate;
import java.util.List;

import org.apache.log4j.Logger;

import com.cts.incomestatement.daos.TxnDao;
import com.cts.incomestatement.daos.TxnDaoBinaryImpl;
import com.cts.incomestatement.daos.TxnDaoImpl;
import com.cts.incomestatement.daos.TxnDaoXMLImpl;
import com.cts.incomestatement.exceptions.DataOperationFailedException;
import com.cts.incomestatement.exceptions.TxnNotFoundException;
import com.cts.incomestatement.models.Statement;
import com.cts.incomestatement.models.Txn;
import com.cts.incomestatement.models.TxnType;

public class TxnServiceImpl implements TxnService {

	private TxnDao txnDao;
	private Logger logger;

	public TxnServiceImpl() {
		//this.txnDao = new TxnDaoBinaryImpl();
		this.txnDao = new TxnDaoXMLImpl();
		this.logger = Logger.getLogger(this.getClass());
	}

	@Override
	public List<Txn> getAll() throws DataOperationFailedException {
		logger.info("getAll attempting");
		return txnDao.findAll();
	}

	@Override
	public Txn getById(long txnId) throws DataOperationFailedException, TxnNotFoundException {
		logger.info("getById attempting");
		if (!txnDao.existsById(txnId)) {
			logger.error("Transaction with id " + txnId + " does not exists!");
			throw new TxnNotFoundException("Transaction with id " + txnId + " does not exists!");
		}
		return txnDao.findById(txnId);
	}

	@Override
	public Txn add(Txn txn) throws DataOperationFailedException {
		logger.debug(txn + " adding");
		return txnDao.save(txn);
	}

	@Override
	public Txn update(Txn txn) throws DataOperationFailedException, TxnNotFoundException {
		logger.debug(txn + "updating");
		if(!txnDao.existsById(txn.getTxnId())) {
			throw new TxnNotFoundException("Transaction with id "+txn.getTxnId()+" does not exists!");
		}
		return txnDao.save(txn);
	}

	@Override
	public void deleteById(long txnId) throws DataOperationFailedException, TxnNotFoundException {
		logger.debug(txnId + "deleting");
		if(!txnDao.existsById(txnId)) {
			logger.error("Transaction with id "+txnId+" does not exists!");
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
		logger.debug(stmt);
		return stmt;
	}

}
