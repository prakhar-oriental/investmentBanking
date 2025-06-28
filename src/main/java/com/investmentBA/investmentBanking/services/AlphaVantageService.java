package com.investmentBA.investmentBanking.services;

import com.investmentBA.investmentBanking.model.InvestmentProduct;
import com.investmentBA.investmentBanking.model.type;
import com.investmentBA.investmentBanking.repository.InvestmentProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AlphaVantageService {

    @Value("${alphavantage.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private InvestmentProductRepo productRepo;

    public void updateNavValue(){
        List<InvestmentProduct> productList = productRepo.findAll();
         for(InvestmentProduct product : productList){
              String name = product.getName();
             String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY"
                     + "&symbol=" + name
                     + "&apikey=" + apiKey;
             try {
                 ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
                 Map<String, Object> body = response.getBody();

                 if (body !=null || body.get("Time Series (Daily)") != null) {
                     Map<String, Object> timeSeries = (Map<String, Object>) body.get("Time Series (Daily)");
                     String latestDate = timeSeries.keySet().stream().findFirst().get();
                     Map<String, String> latestData = (Map<String, String>) timeSeries.get(latestDate);

                     double latestPrice = Double.parseDouble(latestData.get("4. close"));
                      product.setNav(latestPrice);
                      productRepo.save(product);

                 }


             } catch (Exception e) {
                 e.printStackTrace();

             }
         }
    }

    public Optional<InvestmentProduct> fetchProductData(String symbol) {
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY"
                + "&symbol=" + symbol
                + "&apikey=" + apiKey;

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map<String, Object> body = response.getBody();

            if (body == null || body.get("Time Series (Daily)") == null) {
                return Optional.empty();
            }

            Map<String, Object> timeSeries = (Map<String, Object>) body.get("Time Series (Daily)");
            String latestDate = timeSeries.keySet().stream().findFirst().get();
            Map<String, String> latestData = (Map<String, String>) timeSeries.get(latestDate);

            double latestPrice = Double.parseDouble(latestData.get("4. close"));

            InvestmentProduct product = new InvestmentProduct();
            product.setName(symbol); // Use same name unless user provides
            product.setNav(latestPrice);
            product.setType(type.Stock);
            product.setRiskLevel("Moderate");
            product.setCreatedDate(new Date());
            product.setLastUpdatedDate(new Date());

            return Optional.of(product);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}

