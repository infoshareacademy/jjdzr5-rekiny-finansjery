package com.infoshareacademy.services;

import com.infoshareacademy.domain.DailyExchangeRates;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DailyExchangeRatesSearchService {

    private List<DailyExchangeRates> dailyExchangeRates;

    public DailyExchangeRatesSearchService(List<DailyExchangeRates> dailyExchangeRates) {
        this.dailyExchangeRates = new CopyOnWriteArrayList<>(dailyExchangeRates);
    }

    public List<DailyExchangeRates> getDailyExchangeRates() {
        return dailyExchangeRates;
    }

    public DailyExchangeRatesSearchService searchTableNo(String tableNo) {

        Predicate<DailyExchangeRates> searchTableNo = dailyExchangeRates -> dailyExchangeRates.getNo().contains(tableNo.toUpperCase());

        dailyExchangeRates = dailyExchangeRates.stream()
                .filter(searchTableNo)
                .collect(Collectors.toList());

        return this;
    }

    public Optional<DailyExchangeRates> searchEffectiveDate(LocalDate effectiveDate) {

        Predicate<DailyExchangeRates> searchEffectiveDate = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().equals(effectiveDate);

        return dailyExchangeRates.stream()
                .filter(searchEffectiveDate)
                .findAny();
    }

    public DailyExchangeRatesSearchService searchEffectiveDateByTimeRange(LocalDate dateFrom, LocalDate dateTo) {

        Predicate<DailyExchangeRates> searchEffectiveDateFrom = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().compareTo(dateFrom) >= 0;
        Predicate<DailyExchangeRates> searchEffectiveDateTo = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().compareTo(dateTo) <= 0;

        dailyExchangeRates = dailyExchangeRates.stream()
                .filter(searchEffectiveDateFrom.and(searchEffectiveDateTo))
                .collect(Collectors.toList());

        return this;
    }

    public Optional<DailyExchangeRates> searchTradingDate(LocalDate tradingDate) {

        Predicate<DailyExchangeRates> searchTradingDate = dailyExchangeRates -> dailyExchangeRates.getTradingDate().equals(tradingDate);

        return dailyExchangeRates.stream()
                .filter(searchTradingDate)
                .findAny();
    }

    public DailyExchangeRatesSearchService searchTradingDateByTimeRange(LocalDate dateFrom, LocalDate dateTo) {

        Predicate<DailyExchangeRates> searchTradingDateFrom = dailyExchangeRates -> dailyExchangeRates.getTradingDate().compareTo(dateFrom) >= 0;
        Predicate<DailyExchangeRates> searchTradingDateTo = dailyExchangeRates -> dailyExchangeRates.getTradingDate().compareTo(dateTo) <= 0;

        dailyExchangeRates = dailyExchangeRates.stream()
                .filter(searchTradingDateFrom.and(searchTradingDateTo))
                .collect(Collectors.toList());

        return this;
    }

}