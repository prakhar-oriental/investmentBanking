package com.investmentBA.investmentBanking.controller;

import com.investmentBA.investmentBanking.model.InvestmentProduct;
import com.investmentBA.investmentBanking.services.InvestmentProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Investment")
public class InvestmentProductController {
    @Autowired private InvestmentProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody InvestmentProduct investmentProduct)
    {
        InvestmentProduct ans = productService.addProduct(investmentProduct);
        return new ResponseEntity<>(ans, HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProduct()
    {
        List<InvestmentProduct> ans = productService.getAllProduct();
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody InvestmentProduct investmentProduct)
    {
        if(investmentProduct!=null)
        {
            InvestmentProduct ans = productService.updateProduct(investmentProduct);
            return new ResponseEntity<>(ans, HttpStatus.OK);
        }else
            return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id)
    {
        String ans  = productService.deleteProduct(id);
        return new ResponseEntity<>(ans,HttpStatus.OK);
    }
}
