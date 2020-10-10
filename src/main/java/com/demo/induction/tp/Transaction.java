package com.demo.induction.tp;

import java.math.BigDecimal;
import java.util.List;


public class Transaction {
    private String type;
    private BigDecimal amount;
    private String narration;
    
    private List<Transaction> transactionList;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public List<Transaction> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

	@Override
	public String toString() {
		return "Transaction [type=" + type + ", amount=" + amount + ", narration=" + narration + "]";
	}
    
    
}
