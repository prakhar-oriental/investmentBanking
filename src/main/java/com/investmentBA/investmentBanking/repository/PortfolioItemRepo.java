package com.investmentBA.investmentBanking.repository;

import com.investmentBA.investmentBanking.model.PortfolioItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioItemRepo extends JpaRepository<PortfolioItem,Long> {

    @Query(value = "select * from investmentbank.portfolio_item where portfolio_id = :id",nativeQuery = true)
    List<PortfolioItem> findPortItemByPortfolioId(@Param("id") long id);
}
