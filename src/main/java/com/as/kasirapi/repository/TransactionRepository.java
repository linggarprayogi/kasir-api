package com.as.kasirapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.as.kasirapi.model.transaction.TransactionPenjualan;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionPenjualan, Long> {

}
