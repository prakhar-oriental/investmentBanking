package com.investmentBA.investmentBanking.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class SmsService {

    @Value("${sms.api.key}")
    private String apiKey;

    public String sendSMS(String phoneNumber, String message) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.fast2sms.com/dev/bulkV2";

        Map<String, String> params = new HashMap<>();
        params.put("authorization", apiKey);
        params.put("sender_id", "TXTIND");
        params.put("message", message);
        params.put("language", "english");
        params.put("route", "v3");
        params.put("numbers", phoneNumber);

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("authorization", apiKey);

        org.springframework.http.HttpEntity<Map<String, String>> request = new org.springframework.http.HttpEntity<>(params, headers);

        return restTemplate.postForObject(url, request, String.class);
    }
}

