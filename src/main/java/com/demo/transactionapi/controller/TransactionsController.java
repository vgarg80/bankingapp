package com.demo.transactionapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.demo.transactionapi.Repository.TransactionsRepository;
import com.demo.transactionapi.Repository.UserInfoRepository;
import com.demo.transactionapi.dto.TransactionDto;
import com.demo.transactionapi.dto.UserDto;
import com.demo.transactionapi.model.Transactions;
import com.demo.transactionapi.model.UserInfo;
import com.demo.transactionapi.util.JwtTokenUtil;

@RestController
@CrossOrigin(origins = "*")
public class TransactionsController {

	@Autowired
	UserInfoRepository userInfoRepository;
	@Autowired
	TransactionsRepository transactionsRepository;
	
	@PostMapping("/save")
	public ResponseEntity save(@RequestBody UserDto userDto) {
		UserInfo userInfo = new UserInfo();
		userInfo.setContact(userDto.getMobilenumber());
		userInfo.setCreditLimit(userDto.getCreditLimit());
		userInfoRepository.save(userInfo);
		
		return ResponseEntity.ok("Data Inserted !!!");
	}
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody UserDto userDto) {

		UserInfo userInfo = userInfoRepository.findByContact(userDto.getMobilenumber());
		if (userInfo == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		JwtTokenUtil jwtUtil = new JwtTokenUtil();
		String token = jwtUtil.generateToken(userInfo.getContact());

		return new ResponseEntity(token, HttpStatus.OK);
	}

	@PostMapping("/spend")
	public ResponseEntity spend(@RequestHeader("Authorization") String token, @RequestBody TransactionDto transactionDto, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity("Please provide valid data", HttpStatus.BAD_REQUEST);
		}
		if(token == null || token.equals("")) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}	
		token = token.replaceAll("Bearer ", "");
		if(token.equals("")) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		JwtTokenUtil jutil = new JwtTokenUtil();
		String contact = jutil.getContact(token);
		if(contact.equals("-1")) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		UserInfo userInfo = userInfoRepository.findByContact(contact);
		if(userInfo == null) {
			return new ResponseEntity("Please provide valid data", HttpStatus.BAD_REQUEST);
		}
		double creditLimit = userInfo.getCreditLimit();
		if(creditLimit < transactionDto.getAmount()) {
			return new ResponseEntity("Amount beyond your transaction limits", HttpStatus.BAD_REQUEST);
		}
		Transactions trans = new Transactions();
		trans.setDescription(transactionDto.getDescription());
		trans.setTransactionDate(transactionDto.getTransactionDate());
		trans.setUserInfoId(userInfo);
		trans.setTransactionAmount(transactionDto.getAmount());
		transactionsRepository.save(trans);
		
		return ResponseEntity.ok("Data Inserted !!!");
	}
}
