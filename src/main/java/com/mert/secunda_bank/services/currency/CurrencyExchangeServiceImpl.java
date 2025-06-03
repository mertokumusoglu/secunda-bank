package com.mert.secunda_bank.services.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mert.secunda_bank.models.enums.CurrencyTypes;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeServiceImpl.class);

    private final ExchangeRateService exchangeRateService;

    public CurrencyExchangeServiceImpl(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public BigDecimal getSpecificExchangeRate(CurrencyTypes fromCurrency, CurrencyTypes toCurrency) {
        try {
            if (fromCurrency == null || toCurrency == null) {
                throw new IllegalArgumentException("FromCurrency and To Currency cannot be null");
            }
            logger.debug("Attempting to get all rates for base currency {}", fromCurrency);
            Map<CurrencyTypes, BigDecimal> ratesForBaseCurrency = exchangeRateService.getExchangeRates(fromCurrency);
            if (ratesForBaseCurrency == null) {
                logger.error("Received null map of rates for base currency");
                // Special error messages will be added. General exception for now.
                throw new RuntimeException("Runtime exception error occured");
            }
            if (ratesForBaseCurrency.containsKey(toCurrency)) {
                BigDecimal rate = ratesForBaseCurrency.get(toCurrency);
                logger.info("Specific exchange rate found from {} to {}: {}", fromCurrency, toCurrency, rate);
                return rate;
            } else {
                logger.warn(
                        "Specific exchange rate from {} to {} not found in the rates map obtained for base {}. Available rates count: {}",
                        fromCurrency, toCurrency, fromCurrency, ratesForBaseCurrency.size());
                        throw new RuntimeException("Runtime exception error occured");
            }
        } catch (Exception e) {
            logger.error("An unexpected error occured", e.getMessage());
            throw new RuntimeException("Runtime exception error occured");
        }
    }

    @Override
    public BigDecimal convertAmount(BigDecimal amount, CurrencyTypes fromCurrency, CurrencyTypes toCurrency) {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount to convert must be non-null or negative");
        }
        BigDecimal rate = getSpecificExchangeRate(fromCurrency, toCurrency);
        BigDecimal convertedAmount = amount.multiply(rate).setScale(2, RoundingMode.HALF_EVEN);
        logger.info("Converted {} {} to {} {} using rate {}", amount, fromCurrency, convertedAmount, toCurrency, rate);
        return convertedAmount;
    }
}
