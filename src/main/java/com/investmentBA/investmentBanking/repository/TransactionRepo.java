package com.investmentBA.investmentBanking.repository;

import com.investmentBA.investmentBanking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {

    @Query(value = "SELECT * FROM investmentbank.transaction where user_id= :id",nativeQuery = true)
    List<Transaction> getTransactionByUserId(@Param("id") long id);
}
