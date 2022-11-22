package com.cts.incomestatement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cts.incomestatement.entities.Txn;
import com.cts.incomestatement.exceptions.DataOperationFailedException;
import com.cts.incomestatement.services.TxnService;

@Controller
public class StatementController {

	@Autowired
	private TxnService txnService;

	@GetMapping({"","/","/home"})
	public ModelAndView showHome() throws DataOperationFailedException {
		return new ModelAndView("home","txns",txnService.getAll());
	}
	
	@GetMapping("/add")
	public ModelAndView showTxnForm() {
		return new ModelAndView("txn-form","txn",new Txn());
	}
	
	@PostMapping("/add")
	public ModelAndView addTxn(@ModelAttribute("txn") Txn txn) throws DataOperationFailedException {
		txnService.add(txn);
		return new ModelAndView("home","txns",txnService.getAll());
	}
}
