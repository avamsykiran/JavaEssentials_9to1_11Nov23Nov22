package com.cts.incomestatement.daos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.incomestatement.entities.Txn;

public interface TxnDao extends JpaRepository<Txn, Long>{
	@Query("SELECT t FROM Txn t WHERE t.txnDate BETWEEN :start AND :end")
	List<Txn> findAllBetween(LocalDate start,LocalDate end);
}
