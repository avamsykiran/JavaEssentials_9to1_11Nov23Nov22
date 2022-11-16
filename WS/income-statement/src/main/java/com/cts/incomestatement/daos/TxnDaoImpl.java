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

import org.apache.log4j.Logger;

import com.cts.incomestatement.exceptions.DataOperationFailedException;
import com.cts.incomestatement.models.Txn;

public class TxnDaoImpl implements TxnDao {

	public static final String DATA_FILE = "./txns.data";

	private Map<Long, Txn> txns;
	private long seed;
	private Logger logger;
	
	public TxnDaoImpl() {
		this.logger = Logger.getLogger(this.getClass());
	}
	
	private void loadData() throws DataOperationFailedException {
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
				logger.fatal(e.getMessage(),e);
				throw new DataOperationFailedException("Could not load data! Something went wrong!");
			}
			
			logger.debug(txns);
			logger.debug(seed);
		}
	}

	private void saveData() throws DataOperationFailedException {
		if (this.txns != null) {
			logger.info("saving data");
			try (ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
				oout.writeObject(this.txns);
				logger.info("data written");
			} catch (IOException e) {
				logger.fatal(e.getMessage(),e);
				throw new DataOperationFailedException("Could not save data! Something went wrong!");
			}
		}
	}

	@Override
	public List<Txn> findAll() throws DataOperationFailedException {
		loadData();
		logger.debug("findAll returns "+txns.size()+" transactions");
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
			logger.debug("after adding we have "+txns.size()+" transactions");
		} else {
			txns.replace(txn.getTxnId(), txn);
			logger.debug("after updating we have "+txns.size()+" transactions");
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
		logger.debug("after deleting we have "+txns.size()+" transactions");
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
