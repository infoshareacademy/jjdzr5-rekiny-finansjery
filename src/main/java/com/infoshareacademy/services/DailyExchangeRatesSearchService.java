package com.infoshareacademy.services;

import com.infoshareacademy.domain.DailyExchangeRates;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
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

    public DailyExchangeRatesSearchService searchEffectiveDate(LocalDate effectiveDate) {

        Predicate<DailyExchangeRates> searchEffectiveDate = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().equals(effectiveDate);

        dailyExchangeRates = dailyExchangeRates.stream()
                .filter(searchEffectiveDate)
                .collect(Collectors.toList());

        return this;
    }

    public DailyExchangeRatesSearchService searchEffectiveDateByTimeRange(LocalDate dateFrom, LocalDate dateTo) {

        Predicate<DailyExchangeRates> searchEffectiveDateFrom = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().compareTo(dateFrom) >= 0;
        Predicate<DailyExchangeRates> searchEffectiveDateTo = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().compareTo(dateTo) <= 0;

        dailyExchangeRates = dailyExchangeRates.stream()
                .filter(searchEffectiveDateFrom.and(searchEffectiveDateTo))
                .collect(Collectors.toList());

        return this;
    }

    public DailyExchangeRatesSearchService searchTradingDate(LocalDate tradingDate) {

        Predicate<DailyExchangeRates> searchTradingDate = dailyExchangeRates -> dailyExchangeRates.getTradingDate().equals(tradingDate);

        dailyExchangeRates = dailyExchangeRates.stream()
                .filter(searchTradingDate)
                .collect(Collectors.toList());

        return this;
    }

    public DailyExchangeRatesSearchService searchTradingDateByTimeRange(LocalDate dateFrom, LocalDate dateTo) {

        Predicate<DailyExchangeRates> searchTradingDateFrom = dailyExchangeRates -> dailyExchangeRates.getTradingDate().compareTo(dateFrom) >= 0;
        Predicate<DailyExchangeRates> searchTradingDateTo = dailyExchangeRates -> dailyExchangeRates.getTradingDate().compareTo(dateTo) <= 0;

        dailyExchangeRates = dailyExchangeRates.stream()
                .filter(searchTradingDateFrom.and(searchTradingDateTo))
                .collect(Collectors.toList());

        return this;
    }

    public DailyExchangeRatesSearchService forEachDay(Function<DailyExchangeRates, ExchangeRatesSearchService> function) {
        CopyOnWriteArrayList<DailyExchangeRates> list = new CopyOnWriteArrayList<>(dailyExchangeRates);
        for (int i = 0; i < list.size(); i++) {
            DailyExchangeRates exchangeRates = list.get(i).copy();
            ExchangeRatesSearchService exchangeRatesSearchService = function.apply(exchangeRates);
            exchangeRates.setRates(exchangeRatesSearchService.getExchangeRates());
            list.set(i, exchangeRates);
        }
        dailyExchangeRates = list;
        return this;
    }

}