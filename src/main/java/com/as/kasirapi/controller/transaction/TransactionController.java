package com.as.kasirapi.controller.transaction;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.as.kasirapi.model.ResponseModel;
import com.as.kasirapi.model.transaction.TransactionPenjualan;
import com.as.kasirapi.repository.TransactionRepository;
import com.as.kasirapi.service.TransactionService;
import com.as.kasirapi.util.JwtTokenUtil;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private JwtTokenUtil  jwtTokenUtil;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	TransactionService    transactionService;

	@GetMapping("/history")
	public ResponseEntity<?> getHistoryTransaction(HttpServletRequest request) {
		try {
			List<TransactionPenjualan> respons            = new ArrayList<TransactionPenjualan>();
			final String               requestTokenHeader = request.getHeader("Authorization");

			String                     jwtToken           = null;
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer "))
				jwtToken = requestTokenHeader.substring(7);

			if (jwtTokenUtil.validateAccessToken(jwtToken)) {
				respons = transactionRepository.findAll();
			}

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(true, "Success", respons));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseModel(false, e.getMessage()));
		}
	}

	@PostMapping("/process")
	public ResponseEntity<?> processTransaction(HttpServletRequest request,
			List<TransactionPenjualan> transactionPenjualan) {
		try {
			List<TransactionPenjualan> respons            = new ArrayList<TransactionPenjualan>();
			final String               requestTokenHeader = request.getHeader("Authorization");

			String                     jwtToken           = null;
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer "))
				jwtToken = requestTokenHeader.substring(7);

			if (jwtTokenUtil.validateAccessToken(jwtToken)) {
				transactionService.saveTransaction(transactionPenjualan);
			}

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(true, "Success", respons));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseModel(false, e.getMessage()));
		}
	}

}
