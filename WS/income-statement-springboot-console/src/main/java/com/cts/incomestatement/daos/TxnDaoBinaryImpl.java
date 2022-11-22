package com.cts.incomestatement.daos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cts.incomestatement.exceptions.DataOperationFailedException;
import com.cts.incomestatement.models.Txn;

@Repository
public class TxnDaoBinaryImpl extends TxnDaoImpl {

	public static final String DATA_FILE = "./txns.data";

	private Logger logger;

	public TxnDaoBinaryImpl() {
		this.logger = Logger.getLogger(this.getClass());
	}
	
	@Override
	protected void loadData() throws DataOperationFailedException {
		if (this.txns == null) {
			logger.info("loading data");
			logger.debug(DATA_FILE);

			try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
				this.txns = (Map<Long, Txn>) oin.readObject();
				this.seed = this.txns.keySet().stream().reduce((k1, k2) -> k1 > k2 ? k1 : k2).orElse(0L);
			} catch (FileNotFoundException e) {
				logger.error(e);
				this.txns = new TreeMap<>();
				this.seed = 0;
			} catch (IOException | ClassNotFoundException e) {
				logger.fatal(e.getMessage(), e);
				throw new DataOperationFailedException("Could not load data! Something went wrong!");
			}

			logger.debug(txns);
			logger.debug(seed);
		}
	}

	@Override
	protected void saveData() throws DataOperationFailedException {
		if (this.txns != null) {
			logger.info("saving data");
			try (ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
				oout.writeObject(this.txns);
				logger.info("data written");
			} catch (IOException e) {
				logger.fatal(e.getMessage(), e);
				throw new DataOperationFailedException("Could not save data! Something went wrong!");
			}
		}
	}

}
