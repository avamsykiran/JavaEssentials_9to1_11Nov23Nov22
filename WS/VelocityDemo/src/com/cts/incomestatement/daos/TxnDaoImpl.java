package com.cts.incomestatement.daos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cts.incomestatement.exceptions.DataOperationFailedException;
import com.cts.incomestatement.models.Txn;

public abstract class TxnDaoImpl implements TxnDao {

	protected Map<Long, Txn> txns;
	protected long seed;
	
	
	public TxnDaoImpl() {
	
	}
	
	protected abstract void loadData() throws DataOperationFailedException;
	protected abstract void saveData() throws DataOperationFailedException;
	
	@Override
	public List<Txn> findAll() throws DataOperationFailedException {
		loadData();
		return new ArrayList<>(txns.values());
	}

	@Override
	public Txn findById(long txnId) throws DataOperationFailedException {
		loadData();
		return txns.get(txnId);
	}

	@Override
	public Txn save(Txn txn) throws DataOperationFailedException {
		loadData();
		if (txn.getTxnId() == 0) {
			txn.setTxnId(++seed);
			txns.put(txn.getTxnId(), txn);
		} else {
			txns.replace(txn.getTxnId(), txn);
		}
		saveData();
		return txn;
	}

	@Override
	public boolean existsById(long txnId) throws DataOperationFailedException {
		loadData();
		return txns.containsKey(txnId);
	}

	@Override
	public void deleteById(long txnId) throws DataOperationFailedException {
		loadData();
		txns.remove(txnId);
		saveData();
	}

	@Override
	public List<Txn> findAllBetween(LocalDate start, LocalDate end) throws DataOperationFailedException {
		loadData();
		return (txns.values().stream().filter(
				t -> t.getTxnDate().isEqual(start) || t.getTxnDate().isEqual(end) || 
					(t.getTxnDate().isAfter(start) && t.getTxnDate().isBefore(end)))
				.collect(Collectors.toList()));
	}

}
