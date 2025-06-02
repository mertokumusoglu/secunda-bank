package com.mert.secunda_bank.services.currency;

import com.mert.secunda_bank.models.enums.CurrencyTypes;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;    
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService{
    
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

    @Value("${api.exchangerate.key}")
    private String apiKey;

    @Value("${api.exchangerate.baseurl}")
    private String apiBaseUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ExchangeRateServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<CurrencyTypes, BigDecimal> getExchangeRates(CurrencyTypes baseCurrency) {
        String apiUrl = apiBaseUrl + apiKey + "/latest/" + baseCurrency;
        Map<CurrencyTypes, BigDecimal> ratesMap = new EnumMap<>(CurrencyTypes.class);
        try {
            logger.info("Fetching exchange rates from url: {}", apiBaseUrl);
            String jsonResponse = restTemplate.getForObject(apiUrl, String.class);
            logger.debug("API Response: {}", jsonResponse); // !!!!!!!! SENSITIVE DATA

            if(jsonResponse == null) {
                logger.error("Received null response from API: {}", apiBaseUrl);
                return ratesMap;
            }
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode conversionRatesNode = rootNode.path("conversion_rates");

            Iterator<String> currencyCodeIterator = conversionRatesNode.fieldNames();
            while (currencyCodeIterator.hasNext()) {
                String currencyCodeStr = currencyCodeIterator.next();
                JsonNode rateNode = conversionRatesNode.get(currencyCodeStr);
                try {
                    CurrencyTypes currencyType = CurrencyTypes.valueOf(currencyCodeStr.toUpperCase());
                    if (rateNode != null && rateNode.isValueNode()) {
                        String rateText = rateNode.asText();
                        if (rateText != null && !rateText.trim().isEmpty()) {
                            BigDecimal rate = new BigDecimal(rateText);
                            ratesMap.put(currencyType, rate);
                        } else {
                            logger.warn("Rate value for currency code {} is empty or null. Node: {}", currencyCodeStr, rateNode.toString());
                        }
                    } else {
                        logger.warn("Rate for currency code {} is null or not a value node. Node: {}", currencyCodeStr, rateNode == null ? "null" : rateNode.toString());
                    }
                } catch (IllegalArgumentException e) {
                    logger.warn("Unsupported or invalid currency code received from API: '{}' - Skipping.", currencyCodeStr);
                }
            }
            logger.info("Successfully fetched and parsed {} exchange rates for base {}.", ratesMap.size(), baseCurrency);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while fetching exchange rates: {}", e.getMessage(), e);
        }
        return ratesMap;
    }

    @Override
    public BigDecimal getConversionRate(CurrencyTypes fromCurrency, CurrencyTypes toCurrency) {
        if (fromCurrency == toCurrency) {
            return BigDecimal.ONE;
        }
        Map<CurrencyTypes, BigDecimal> ratesFromBase = getExchangeRates(fromCurrency);
        if( ratesFromBase.containsKey(toCurrency)) {
            return ratesFromBase.get(toCurrency);
        } else {
            logger.warn("Direct conversion rate from {} to {} not found using {} as base", fromCurrency, toCurrency, fromCurrency);
            logger.error("Could not determine conversion rate from {} to {}.", fromCurrency, toCurrency);
            return null;
        }
    }
}
