package com.cts.incomestatement.daos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.cts.incomestatement.models.Txn;

public class TxnDaoImpl implements TxnDao {

	public static final String DATA_FILE = "./txns.data";

	private Map<Long, Txn> txns;
	private long seed;

	private void loadData() {
		if (this.txns == null) {
			try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
				this.txns = (Map<Long, Txn>) oin.readObject();
				this.seed = this.txns.keySet().stream().reduce((k1,k2) -> k1>k2?k1:k2).orElse(0L);
			} catch (FileNotFoundException e) {
				this.txns = new TreeMap<>();
				this.seed = 0;
			} catch (IOException | ClassNotFoundException e) {
				// log the exception and swallow it and raise a user defined exception.
			}
		}
	}

	private void saveData() {
		if (this.txns != null) {
			try (ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
				oout.writeObject(this.txns);
			} catch (IOException e) {
				// log the exception and swallow it and raise a user defined exception.
			}
		}
	}
	
	@Override
	public List<Txn> findAll() {
		loadData();
		return new ArrayList<>(txns.values());
	}

	@Override
	public Txn findById(long txnId) {
		loadData();
		return txns.get(txnId);
	}

	@Override
	public Txn save(Txn txn) {
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
	public boolean existsById(long txnId) {
		loadData();
		return txns.containsKey(txnId);
	}

	@Override
	public void deleteById(long txnId) {
		loadData();
		txns.remove(txnId);
		saveData();
	}

}
