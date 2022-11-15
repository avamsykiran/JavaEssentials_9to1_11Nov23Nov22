package com.cts.incomestatement.daos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.cts.incomestatement.exceptions.DataOperationFailedException;
import com.cts.incomestatement.models.Txn;

public class TxnDaoImpl implements TxnDao {

	public static final String DATA_FILE = "./txns.data";

	private Map<Long, Txn> txns;
	private long seed;

	private void loadData() throws DataOperationFailedException {
		if (this.txns == null) {
			try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
				this.txns = (Map<Long, Txn>) oin.readObject();
				this.seed = this.txns.keySet().stream().reduce((k1, k2) -> k1 > k2 ? k1 : k2).orElse(0L);
			} catch (FileNotFoundException e) {
				this.txns = new TreeMap<>();
				this.seed = 0;
			} catch (IOException | ClassNotFoundException e) {
				// log the exception and swallow it and raise a user defined exception.
				throw new DataOperationFailedException("Could not load data! Something went wrong!");
			}
		}
	}

	private void saveData() throws DataOperationFailedException {
		if (this.txns != null) {
			try (ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
				oout.writeObject(this.txns);
			} catch (IOException e) {
				// log the exception and swallow it and raise a user defined exception.
				throw new DataOperationFailedException("Could not save data! Something went wrong!");
			}
		}
	}

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
