package com.mert.secunda_bank.services.currency;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.mert.secunda_bank.models.enums.CurrencyTypes;

@Service
public interface CurrencyExchangeService {
    BigDecimal getSpecificExchangeRate(CurrencyTypes fromCurrency, CurrencyTypes toCurrency);
    BigDecimal convertAmount(BigDecimal amount, CurrencyTypes fromCurrency, CurrencyTypes toCurrency);
}
