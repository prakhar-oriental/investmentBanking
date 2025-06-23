package com.investmentBA.investmentBanking.repository;

import com.investmentBA.investmentBanking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {


}
