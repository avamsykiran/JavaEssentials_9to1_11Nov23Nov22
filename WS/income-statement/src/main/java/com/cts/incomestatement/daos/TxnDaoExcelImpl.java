package com.cts.incomestatement.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.dom4j.DocumentException;

import com.cts.incomestatement.exceptions.DataOperationFailedException;
import com.cts.incomestatement.models.Txn;

public class TxnDaoExcelImpl extends TxnDaoImpl {

	public static final String DATA_FILE = "./txns.xls";

	private Logger logger;

	public TxnDaoExcelImpl() {
		this.logger = Logger.getLogger(this.getClass());
	}

	@Override
	protected void loadData() throws DataOperationFailedException {
		if (this.txns == null) {
			logger.info("loading data");
			logger.debug(DATA_FILE);

			this.txns = new TreeMap<>();
			this.seed = 0;

			try (FileInputStream fin = new FileInputStream(DATA_FILE)) {

				Workbook wb = new HSSFWorkbook(fin);
				
				Sheet sh = wb.getSheet("Transactions");
				
				//extract ros and cells.
				
				
				this.seed = this.txns.keySet().stream().reduce((k1, k2) -> k1 > k2 ? k1 : k2).orElse(0L);

			}catch (FileNotFoundException e) {
				logger.fatal(e.getMessage(), e);
			}catch (IOException e) {
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

			try (FileOutputStream fout = new FileOutputStream(DATA_FILE)) {

				Workbook wb = new HSSFWorkbook();

				Sheet sheet = wb.createSheet("Transactions");
				int rownum = 0;
				for (Txn txn : txns.values()) {
					Row row = sheet.createRow(++rownum);
					Cell c1 = row.createCell(1, Cell.CELL_TYPE_NUMERIC);
					Cell c2 = row.createCell(2, Cell.CELL_TYPE_STRING);
					Cell c3 = row.createCell(3, Cell.CELL_TYPE_NUMERIC);
					Cell c4 = row.createCell(4, Cell.CELL_TYPE_STRING);
					Cell c5 = row.createCell(5, Cell.CELL_TYPE_STRING);

					c1.setCellValue(txn.getTxnId());
					c2.setCellValue(txn.getHeader());
					c3.setCellValue(txn.getAmount());
					c4.setCellValue(txn.getType().toString());
					c5.setCellValue(txn.getTxnDate().toString());
				}

				wb.write(fout);

				logger.info("data written");
			} catch (IOException e) {
				logger.fatal(e.getMessage(), e);
				throw new DataOperationFailedException("Could not save data! Something went wrong!");
			}
		}
	}

}
