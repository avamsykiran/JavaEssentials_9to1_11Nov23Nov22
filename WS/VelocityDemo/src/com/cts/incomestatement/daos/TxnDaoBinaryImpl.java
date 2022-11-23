package com.cts.incomestatement.daos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.TreeMap;

import com.cts.incomestatement.exceptions.DataOperationFailedException;
import com.cts.incomestatement.models.Txn;

public class TxnDaoBinaryImpl extends TxnDaoImpl {

	public static final String DATA_FILE = "./txns.data";

	

	public TxnDaoBinaryImpl() {
	}
	
	@Override
	protected void loadData() throws DataOperationFailedException {
		if (this.txns == null) {
	
			try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
				this.txns = (Map<Long, Txn>) oin.readObject();
				this.seed = this.txns.keySet().stream().reduce((k1, k2) -> k1 > k2 ? k1 : k2).orElse(0L);
			} catch (FileNotFoundException e) {
				this.txns = new TreeMap<>();
				this.seed = 0;
			} catch (IOException | ClassNotFoundException e) {
				throw new DataOperationFailedException("Could not load data! Something went wrong!");
			}
		}
	}

	@Override
	protected void saveData() throws DataOperationFailedException {
		if (this.txns != null) {
			try (ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
				oout.writeObject(this.txns);
			} catch (IOException e) {
				throw new DataOperationFailedException("Could not save data! Something went wrong!");
			}
		}
	}

}
