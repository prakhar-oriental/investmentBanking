package com.investmentBA.investmentBanking.services;

import com.investmentBA.investmentBanking.model.InvestmentProduct;
import com.investmentBA.investmentBanking.repository.InvestmentProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InvestmentProductService {

    @Autowired
    InvestmentProductRepo investmentProductRepo;

    public InvestmentProduct addProduct(InvestmentProduct investmentProduct)
    {
        if(investmentProduct!=null)
        {
            investmentProduct.setCreatedDate(new Date());
            investmentProduct.setLastUpdatedDate(new Date());
          return investmentProductRepo.save(investmentProduct);
        }else {
            return null;
        }

    }
    public List<InvestmentProduct> getAllProduct()
    {
        return investmentProductRepo.findAll();
    }

    public InvestmentProduct updateProduct(InvestmentProduct investmentProduct)
    {
        List<InvestmentProduct> product =  investmentProductRepo.findByName(investmentProduct.getName());
        for(InvestmentProduct product1:product)
        {
            if(product1.getName().equals(investmentProduct.getName()))
            {
                product1.setNav(investmentProduct.getNav());
                product1.setName(investmentProduct.getName());
                product1.setType(investmentProduct.getType());
//                product1.setCreatedDate(investmentProduct.getCreatedDate());
//                product1.setLastUpdatedDate(investmentProduct.getLastUpdatedDate());
                return investmentProductRepo.save(product1);
            }
        }

        return null;
    }
    public String deleteProduct(long id)
    {
        investmentProductRepo.deleteById(id);
        return "Deleted InvestmentProduct Succespully";
    }
}

