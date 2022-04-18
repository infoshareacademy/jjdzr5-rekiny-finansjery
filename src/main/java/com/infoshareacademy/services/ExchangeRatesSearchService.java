package com.infoshareacademy.services;

import com.infoshareacademy.domain.DailyExchangeRates;
import com.infoshareacademy.domain.ExchangeRate;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExchangeRatesSearchService {

    private final List<ExchangeRate> exchangeRates;

    public ExchangeRatesSearchService(List<ExchangeRate> exchangeRates) {
        this.exchangeRates = new CopyOnWriteArrayList<>(exchangeRates);
    }

    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public List<ExchangeRate> searchCurrency(String currency) {

        Predicate<ExchangeRate> searchCurrency = exchangeRate -> exchangeRate.getCurrency().contains(currency.toLowerCase());

        return exchangeRates.stream()
                .filter(searchCurrency)
                .collect(Collectors.toList());
    }

    public ExchangeRatesFiltrationService searchCurrencyForFiltration(String currency) {
        return new ExchangeRatesFiltrationService(searchCurrency(currency));
    }

    public Optional<ExchangeRate> searchCode(String code) {

        Predicate<ExchangeRate> searchCode = exchangeRate -> exchangeRate.getCode().contains(code.toUpperCase());

        return exchangeRates.stream()
                .filter(searchCode)
                .findAny();
    }

    public List<ExchangeRate> searchWidely(String phrases){
        String[] parts = phrases.split(" ");

        Set<ExchangeRate> result = new HashSet<>();
        Supplier<Stream<ExchangeRate>> streamSupplier = () -> exchangeRates.stream();

        result.addAll(streamSupplier.get().filter(exchangeRate -> {
            for(String phrase : parts) {
                if(exchangeRate.getCode().contains(phrase.toUpperCase()) ||
                        exchangeRate.getCurrency().toLowerCase().contains(phrase.toLowerCase())) return true;
            }
            return false;
        }).collect(Collectors.toList()));
        return result.stream().collect(Collectors.toList());
    }
}