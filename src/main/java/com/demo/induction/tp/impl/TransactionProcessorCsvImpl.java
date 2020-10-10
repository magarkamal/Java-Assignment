package com.demo.induction.tp.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.demo.induction.tp.Transaction;
import com.demo.induction.tp.TransactionProcessor;
import com.demo.induction.tp.Violation;

public class TransactionProcessorCsvImpl implements TransactionProcessor {

	List<Transaction> trList = new ArrayList<>();
	List<Violation> violationList = new ArrayList<>();

	@Override
	public void importTransactions(InputStream is) {

		System.out.println("######## Import Transaction ########");
		Transaction tr;
		InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);

		BufferedReader br = new BufferedReader(isr);
		String line;
		try {
			while ((line = br.readLine()) != null) {

				String[] attributes = line.split(",");
				tr = new Transaction();
				tr.setType(attributes[0]);
				tr.setAmount(new BigDecimal(attributes[1]));
				tr.setNarration(attributes[2]);

				trList.add(tr);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tr = new Transaction();
		tr.setTransactionList(trList);


	}

	@Override
	public List<Transaction> getImportedTransactions() {
		System.out.println("######## Transaction List ########");

		return this.trList;
	}

	@Override
	public List<Violation> validate() {
		System.out.println("######## Validate Transaction ########");
		int i = 1;
		Violation violation = new Violation();
		List<Transaction> transactionsList = this.trList;
		for (Transaction transaction : transactionsList) {
			
			if (transaction.getType().equalsIgnoreCase("d") || transaction.getType().equalsIgnoreCase("c")) {
				violation.setOrder(i);
				
			} else {
				violation.setOrder(i);
				violation.setProperty("type");
				violation.setDescription("type invalid");
			}
			violationList.add(violation);
			i++;
		}
		Boolean result = isBalanced();
		if(result.booleanValue() == false) {
			violation.setProperty("amount");
			violation.setDescription("amount not balanced");
			violationList.add(violation);
		}
		return this.violationList;
	}

	@Override
	public boolean isBalanced() {
		System.out.println("######## Total Transaction Amount ########");
		BigDecimal drTot = new BigDecimal("0");
		BigDecimal crTot = new BigDecimal("0");
		
		
		List<Transaction> transactionsList = this.trList;
    	
		
		for (Transaction transaction : transactionsList) {
			if (transaction.getType().equalsIgnoreCase("d")) {
				drTot = drTot.add(new BigDecimal(transaction.getAmount().toString()));
			} else {
				crTot = crTot.add(new BigDecimal(transaction.getAmount().toString()));
			}
		}
		
		System.out.println("Debit:" + drTot);
		System.out.println("Credit:" + crTot);
		if (drTot.compareTo(crTot) == 0) {
			return true;
		} else {
			return false;
		}

	}

}
