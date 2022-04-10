package com.infoshareacademy.services;

import com.infoshareacademy.domain.DailyExchangeRates;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DailyExchangeRatesSearchService {

    private final List<DailyExchangeRates> dailyExchangeRates;

    public DailyExchangeRatesSearchService(List<DailyExchangeRates> dailyExchangeRates) {
        this.dailyExchangeRates = new CopyOnWriteArrayList<>(dailyExchangeRates);
    }

    public List<DailyExchangeRates> getDailyExchangeRates() {
        return dailyExchangeRates;
    }

    public List<DailyExchangeRates> searchTableNo(String tableNo) {

        Predicate<DailyExchangeRates> searchTableNo = dailyExchangeRates -> dailyExchangeRates.getNo().contains(tableNo.toUpperCase());

        return dailyExchangeRates.stream()
                .filter(searchTableNo)
                .collect(Collectors.toList());
    }

    public DailyExchangeRatesFiltrationService searchTableNoForFiltration(String tableNo) {
        return new DailyExchangeRatesFiltrationService(searchTableNo(tableNo));
    }

    public List<DailyExchangeRates> searchEffectiveDateByTimeRange(LocalDate dateFrom, LocalDate dateTo) {

        Predicate<DailyExchangeRates> searchEffectiveDateFrom = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().compareTo(dateFrom) >= 0;
        Predicate<DailyExchangeRates> searchEffectiveDateTo = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().compareTo(dateTo) <= 0;

        return dailyExchangeRates.stream()
                .filter(searchEffectiveDateFrom.and(searchEffectiveDateTo))
                .collect(Collectors.toList());
    }

    public DailyExchangeRatesFiltrationService searchEffectiveDateByTimeRangeForFiltration(LocalDate dateFrom, LocalDate dateTo) {
        return new DailyExchangeRatesFiltrationService(searchEffectiveDateByTimeRange(dateFrom, dateTo));
    }

    public List<DailyExchangeRates> searchTradingDateByTimeRange(LocalDate dateFrom, LocalDate dateTo) {

        Predicate<DailyExchangeRates> searchTradingDateFrom = dailyExchangeRates -> dailyExchangeRates.getTradingDate().compareTo(dateFrom) >= 0;
        Predicate<DailyExchangeRates> searchTradingDateTo = dailyExchangeRates -> dailyExchangeRates.getTradingDate().compareTo(dateTo) <= 0;

        return dailyExchangeRates.stream()
                .filter(searchTradingDateFrom.and(searchTradingDateTo))
                .collect(Collectors.toList());
    }

    public DailyExchangeRatesFiltrationService searchTradingDateByTimeRangeForFiltration(LocalDate dateFrom, LocalDate dateTo) {
        return new DailyExchangeRatesFiltrationService(searchTradingDateByTimeRange(dateFrom, dateTo));
    }

    public List<DailyExchangeRates> forEachDay(Consumer<DailyExchangeRates> consumer) {

        CopyOnWriteArrayList<DailyExchangeRates> list = new CopyOnWriteArrayList<>(dailyExchangeRates);
        for (int i = 0; i < list.size(); i++) {
            DailyExchangeRates exchangeRates = list.get(i).copy();
            consumer.accept(exchangeRates);
            list.set(i, exchangeRates);
        }

        return list;
    }

    public DailyExchangeRatesFiltrationService forEachDayForFiltration(Consumer<DailyExchangeRates> consumer) {
        return new DailyExchangeRatesFiltrationService(forEachDay(consumer));
    }


    public Optional<DailyExchangeRates> searchEffectiveDate(LocalDate effectiveDate) {

        Predicate<DailyExchangeRates> searchEffectiveDate = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().equals(effectiveDate);

        return dailyExchangeRates.stream()
                .filter(searchEffectiveDate)
                .findAny();
    }

    public Optional<DailyExchangeRates> searchTradingDate(LocalDate tradingDate) {

        Predicate<DailyExchangeRates> searchTradingDate = dailyExchangeRates -> dailyExchangeRates.getTradingDate().equals(tradingDate);

        return dailyExchangeRates.stream()
                .filter(searchTradingDate)
                .findAny();
    }

}