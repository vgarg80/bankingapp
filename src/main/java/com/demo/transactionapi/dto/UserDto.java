package com.demo.transactionapi.dto;

import javax.validation.constraints.NotNull;

public class UserDto {

	@NotNull
	private String mobilenumber;
	 @NotNull
	private double creditLimit;
	 
	public String getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public double getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}
	 
	 
}
