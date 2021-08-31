package com.demo.transactionapi.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;


public class TransactionDto {
	
	@NotNull
	private Date transactionDate;
	@NotNull
	private String description;
	@NotNull
	private Double amount;
	
	public TransactionDto() {
	
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
