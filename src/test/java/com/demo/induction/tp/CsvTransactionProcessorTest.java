package com.demo.induction.tp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.demo.induction.tp.impl.TransactionProcessorCsvImpl;

public class CsvTransactionProcessorTest {
	
	 private TransactionProcessor tp;

	    @Before
	    public void setUp() {
	    	tp = new TransactionProcessorCsvImpl();
	    }

	    @Test
	    public void importedTransactionTest() {
	    	String fileName = "src/resources/mycsvfile.csv";
			try {
				InputStream is = new FileInputStream(fileName);
				tp.importTransactions(is);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			List<Transaction> transactionsList = tp.getImportedTransactions();
	    	System.out.println("listSize:"+transactionsList.size());
	    	for (Transaction transaction : transactionsList) {
				System.out.println("myList:" + transaction.toString());
			}

	    }
	    
	    @Test
	    public void validationTest() {
	    	String fileName = "src/resources/mycsvfile.csv";
			try {
				InputStream is = new FileInputStream(fileName);
				tp.importTransactions(is);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Violation> violationList = tp.validate();
	    	System.out.println("violationlistSize:"+violationList.size());
	    	for (Violation violation : violationList) {
				System.out.println("violationList:" + violationList.toString());
			}
			
	    }
	    
	    @Test
	    public void isBalancedTest(){
	    	String fileName = "src/resources/mycsvfile.csv";
			try {
				InputStream is = new FileInputStream(fileName);
				tp.importTransactions(is);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	String result = String.valueOf(tp.isBalanced());
	    	System.out.println("result:"+result);
	    }
	    
	   
}
