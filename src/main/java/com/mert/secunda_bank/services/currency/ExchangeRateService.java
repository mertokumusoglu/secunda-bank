package com.mert.secunda_bank.services.currency;

import java.math.BigDecimal;
import java.util.Map;

import com.mert.secunda_bank.models.enums.CurrencyTypes;

public interface ExchangeRateService {
    
    Map<CurrencyTypes, BigDecimal> getExchangeRates(CurrencyTypes baseCurrency);

    BigDecimal getConversionRate(CurrencyTypes fromCurrency, CurrencyTypes toCurrency);
}
