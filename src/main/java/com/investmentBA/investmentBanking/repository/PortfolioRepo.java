package com.investmentBA.investmentBanking.repository;

import com.investmentBA.investmentBanking.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.sound.sampled.Port;
import java.math.BigInteger;

@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio,Long> {
    @Query(value = "select * from investmentbank.portfolio where user_id= :id", nativeQuery = true)
    Portfolio findPortfolioByUserId(@Param("id") long id);
}
