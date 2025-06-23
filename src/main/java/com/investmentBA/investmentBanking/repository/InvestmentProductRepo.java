package com.investmentBA.investmentBanking.repository;

import com.investmentBA.investmentBanking.model.InvestmentProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InvestmentProductRepo extends JpaRepository<InvestmentProduct,Long> {

    public List<InvestmentProduct> findByName(String name);
}
