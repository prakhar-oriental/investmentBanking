package com.investmentBA.investmentBanking.repository;

import com.investmentBA.investmentBanking.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio,Long> {
}
