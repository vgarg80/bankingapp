package com.demo.transactionapi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.transactionapi.model.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
	
}
