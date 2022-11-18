package com.cts.incomestatement.daos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.cts.incomestatement.exceptions.DataOperationFailedException;
import com.cts.incomestatement.models.Txn;
import com.cts.incomestatement.models.TxnType;

public class TxnDaoXMLImpl extends TxnDaoImpl {

	public static final String DATA_FILE = "./txns.xml";

	private Logger logger;

	public TxnDaoXMLImpl() {
		this.logger = Logger.getLogger(this.getClass());
	}

	@Override
	protected void loadData() throws DataOperationFailedException {
		if (this.txns == null) {
			logger.info("loading data");
			logger.debug(DATA_FILE);

			this.txns = new TreeMap<>();
			this.seed = 0;

			try {
				File file = new File(DATA_FILE);

				if (file.exists()) {
					SAXReader reader = new SAXReader();
					Document doc = reader.read(file);

					List<Node> nodes = doc.selectNodes("/txns/txn");

					nodes.stream().forEach(node -> {

						Txn txn = new Txn();
						txn.setTxnId(Long.parseLong(node.valueOf("@txnId")));
						txn.setHeader(node.selectSingleNode("header").getText());
						txn.setAmount(Double.valueOf(node.selectSingleNode("amount").getText()));
						txn.setType(TxnType.valueOf(node.selectSingleNode("type").getText()));
						txn.setTxnDate(LocalDate.parse(node.selectSingleNode("txndate").getText()));

						this.txns.put(txn.getTxnId(), txn);
					});

					this.seed = this.txns.keySet().stream().reduce((k1, k2) -> k1 > k2 ? k1 : k2).orElse(0L);
				}
			} catch (DocumentException e) {
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

			try {

				Document doc = DocumentHelper.createDocument();
				Element rootEle = doc.addElement("txns");
				txns.values().stream().forEach(txn -> {
					Element txnEle = rootEle.addElement("txn");
					txnEle.addAttribute("txnId", String.valueOf(txn.getTxnId()));
					txnEle.addElement("header").addText(txn.getHeader());
					txnEle.addElement("amount").addText(String.valueOf(txn.getAmount()));
					txnEle.addElement("type").addText(txn.getType().toString());
					txnEle.addElement("txndate").addText(txn.getTxnDate().toString());
				});

				OutputFormat format = OutputFormat.createPrettyPrint();
				XMLWriter writer = new XMLWriter(new FileOutputStream(DATA_FILE), format);
				writer.write(doc);
				/*XMLWriter writer2 = new XMLWriter(System.out, format);
				writer2.write(doc);*/
				
				logger.info("data written");
			} catch (IOException e) {
				logger.fatal(e.getMessage(), e);
				throw new DataOperationFailedException("Could not save data! Something went wrong!");
			}
		}
	}

}
