package com.infoshareacademy.services;

import com.infoshareacademy.domain.DailyExchangeRates;

import java.time.LocalDate;
import java.time.Month;
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

    public Optional<DailyExchangeRates> searchTableNo(int year, String no) {

        Predicate<DailyExchangeRates> searchTableNo = dailyExchangeRates -> dailyExchangeRates.getNo().contains(no.toLowerCase());
        Predicate<DailyExchangeRates> searchTableYear = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().getYear() == year;

        return dailyExchangeRates.stream()
                .filter(searchTableNo.and(searchTableYear))
                .findAny();
    }

    public Optional<DailyExchangeRates> searchEffectiveDate(LocalDate effectiveDate) {

        Predicate<DailyExchangeRates> searchEffectiveDate = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().equals(effectiveDate);

        return dailyExchangeRates.stream()
                .filter(searchEffectiveDate)
                .findAny();
    }

    public DailyExchangeRatesSearchService searchEffectiveDateByMonth(int year, Month month) {

        boolean leapYear = LocalDate.of(year, 1, 1).isLeapYear();
        Predicate<DailyExchangeRates> searchEffectiveDateFrom = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().compareTo(LocalDate.of(year, month, 1)) >= 0;
        Predicate<DailyExchangeRates> searchEffectiveDateTo = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().compareTo(LocalDate.of(year, month, month.length(leapYear))) <= 0;

        dailyExchangeRates = dailyExchangeRates.stream()
                .filter(searchEffectiveDateFrom.and(searchEffectiveDateTo))
                .collect(Collectors.toList());

        return this;
    }

    public DailyExchangeRatesSearchService searchEffectiveDateByYear(int year) {

        Predicate<DailyExchangeRates> searchEffectiveDateFrom = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().compareTo(LocalDate.of(year, Month.JANUARY, 1)) >= 0;
        Predicate<DailyExchangeRates> searchEffectiveDateTo = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().compareTo(LocalDate.of(year, Month.DECEMBER, 31)) <= 0;

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

    public DailyExchangeRatesSearchService searchTradingDateByMonth(int year, Month month) {

        boolean leapYear = LocalDate.of(year, 1, 1).isLeapYear();
        Predicate<DailyExchangeRates> searchTradingDateFrom = dailyExchangeRates -> dailyExchangeRates.getTradingDate().compareTo(LocalDate.of(year, month, 1)) >= 0;
        Predicate<DailyExchangeRates> searchTradingDateTo = dailyExchangeRates -> dailyExchangeRates.getTradingDate().compareTo(LocalDate.of(year, month, month.length(leapYear))) <= 0;

        dailyExchangeRates = dailyExchangeRates.stream()
                .filter(searchTradingDateFrom.and(searchTradingDateTo))
                .collect(Collectors.toList());

        return this;
    }

    public DailyExchangeRatesSearchService searchTradingDateByYear(int year) {

        Predicate<DailyExchangeRates> searchTradingDateFrom = dailyExchangeRates -> dailyExchangeRates.getTradingDate().compareTo(LocalDate.of(year, Month.JANUARY, 1)) >= 0;
        Predicate<DailyExchangeRates> searchTradingDateTo = dailyExchangeRates -> dailyExchangeRates.getTradingDate().compareTo(LocalDate.of(year, Month.DECEMBER, 31)) <= 0;

        dailyExchangeRates = dailyExchangeRates.stream()
                .filter(searchTradingDateFrom.and(searchTradingDateTo))
                .collect(Collectors.toList());

        return this;
    }

}
