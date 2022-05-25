package com.as.kasirapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.as.kasirapi.model.transaction.TransactionPenjualan;
import com.as.kasirapi.repository.TransactionRepository;

@Service
@Transactional
public class TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;

	public List<TransactionPenjualan> saveTransaction(List<TransactionPenjualan> transactionPenjualanLst)
			throws Exception {

		transactionRepository.saveAll(transactionPenjualanLst);

		return transactionPenjualanLst;
	}
}
