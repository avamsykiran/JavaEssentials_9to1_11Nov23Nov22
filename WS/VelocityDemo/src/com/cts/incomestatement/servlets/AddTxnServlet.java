package com.cts.incomestatement.servlets;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityViewServlet;

import com.cts.incomestatement.exceptions.DataOperationFailedException;
import com.cts.incomestatement.models.Txn;
import com.cts.incomestatement.models.TxnType;
import com.cts.incomestatement.services.TxnService;
import com.cts.incomestatement.services.TxnServiceImpl;

/**
 * Servlet implementation class TxnListServlet
 */
@WebServlet("/addTxn")
public class AddTxnServlet extends VelocityViewServlet {

	private TxnService txnService = new TxnServiceImpl();

	@Override
	protected Template handleRequest(HttpServletRequest request, HttpServletResponse response, Context ctx) {

		Template template=null;
		
		if(request.getMethod().equals("GET")) {
			template = getTemplate("templates/txn-form.vm");	
		}else {
			Txn txn = new Txn();
			txn.setHeader(request.getParameter("header"));
			txn.setType(TxnType.valueOf(request.getParameter("type")));
			txn.setTxnDate(LocalDate.parse(request.getParameter("txnDate")));
			txn.setAmount(Double.parseDouble(request.getParameter("amount")));
			
			try {
				txnService.add(txn);
				ctx.put("txns", txnService.getAll());
			} catch (DataOperationFailedException e) {
				ctx.put("errMsg", e.getMessage());
			}

			template = getTemplate("templates/index.vm");
		}
		
		response.setHeader("Template Returned", "Success");
		return template;
	}

}
