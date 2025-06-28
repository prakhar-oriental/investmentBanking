package com.investmentBA.investmentBanking.repository;

import com.investmentBA.investmentBanking.model.Userr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Userr, Long> {
    Userr findByUsername(String username);

}
