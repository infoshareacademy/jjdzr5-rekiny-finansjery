package com.infoshareacademy.services;

import com.infoshareacademy.domain.ExchangeRate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ExchangeRatesService extends CopyOnWriteArrayList<ExchangeRate> {

    private List<ExchangeRate> exchangeRates;

    public ExchangeRatesService(List<ExchangeRate> list){
        exchangeRates = new CopyOnWriteArrayList<>(list);
    }

    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public ExchangeRatesService filterBySellPriceFrom(double min){
        exchangeRates = exchangeRates.stream()
                .filter((currency -> min <= currency.getAsk()))
                .collect(Collectors.toList());
        return this;
    }
    public ExchangeRatesService filterBySellPriceTo(double max){
        exchangeRates = exchangeRates.stream()
                .filter((currency -> max >= currency.getAsk()))
                .collect(Collectors.toList());
        return this;
    }
    public ExchangeRatesService filterByBuyPriceFrom(double min){
        exchangeRates =  exchangeRates.stream()
                .filter((currency -> min <= currency.getBid()))
                .collect(Collectors.toList());
        return this;
    }
    public ExchangeRatesService filterByBuyPriceTo(double max){
        exchangeRates = exchangeRates.stream()
                .filter((currency -> max >= currency.getBid()))
                .collect(Collectors.toList());
        return this;
    }

    public ExchangeRatesService filterByShortName(String... selectedCurrencies){
        List<String> selectedCurrenciesList = Arrays.asList(selectedCurrencies);
        exchangeRates = exchangeRates.stream()
                .filter((currency ->
                        selectedCurrenciesList.contains(currency.getCode())
                ))
                .collect(Collectors.toList());
        return this;
    }
}
