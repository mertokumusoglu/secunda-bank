package com.mert.secunda_bank.controllers;

import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.services.currency.ExchangeRateService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;    
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/currencies")
public class CurrencyController {

    private final ExchangeRateService exchangeRateService;
    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    public CurrencyController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }
    
    @GetMapping("/rates/{baseCurrencyCode}")
    public ResponseEntity<Map<CurrencyTypes, BigDecimal>> getAllExchangeRatesForBaseCurrency(
            @PathVariable String baseCurrencyCode) {
                try {
                    logger.info("Attempting to retrieve exchange rates for base currency: {}", baseCurrencyCode);
                    CurrencyTypes baseCurrency  = CurrencyTypes.valueOf(baseCurrencyCode.toUpperCase());
                    Map<CurrencyTypes, BigDecimal> rates = exchangeRateService.getExchangeRates(baseCurrency);
                    if(rates == null || rates.isEmpty()) {
                        logger.warn("No exchange rates found for base currency: {}. Returning NOT_FOUND.", baseCurrencyCode);
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                    }
                    logger.info("Successfully retrieved {} exchange rates for base currency: {}", rates.size(), baseCurrencyCode);
                    return ResponseEntity.ok(rates);
                } catch (IllegalArgumentException e) {
                    logger.warn("Invalid currency code provided: {}. Returning BAD_REQUEST.", baseCurrencyCode, e);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                } catch (Exception e) {
                    logger.error("Unexpected error while retrieving exchange rates for base currency {}: {}", baseCurrencyCode, e.getMessage(), e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                }
    }
    
    @GetMapping("/supported-codes")
    public ResponseEntity<List<CurrencyTypes>> getSupportedCurrencies() {
        try {
            List<CurrencyTypes> supportedCurrencies = Arrays.asList(CurrencyTypes.values());
            logger.info("Attempting to retrieve supported currency codes");
            return ResponseEntity.ok(supportedCurrencies);
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving supported currency codes: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
