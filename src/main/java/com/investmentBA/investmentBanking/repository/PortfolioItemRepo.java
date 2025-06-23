package com.investmentBA.investmentBanking.repository;

import com.investmentBA.investmentBanking.model.PortfolioItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioItemRepo extends JpaRepository<PortfolioItem,Long> {
}
