package com.infoshareacademy.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ExchangeRatesArchiveTable extends Vector<DailyExchangeRates> {
    public ExchangeRatesArchiveTable() {
    }

    public ExchangeRatesArchiveTable(List<DailyExchangeRates> list) {
        super(list);
    }

    public Optional<DailyExchangeRates> getLatestEffectiveDate() {
        return this.stream().max(Comparator.comparing(DailyExchangeRates::getEffectiveDate));
    }

    public Optional<DailyExchangeRates> searchTableNo(int year, String no) {

        Predicate<DailyExchangeRates> searchTableNo = dailyExchangeRates -> dailyExchangeRates.getNo().contains(no.toLowerCase());
        Predicate<DailyExchangeRates> searchTableYear = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().getYear() == year;

        return this.stream()
                .filter(searchTableNo.and(searchTableYear))
                .findAny();
    }

    public Optional<DailyExchangeRates> searchEffectiveDate(LocalDate effectiveDate) {

        Predicate<DailyExchangeRates> searchEffectiveDate = dailyExchangeRates -> dailyExchangeRates.getEffectiveDate().equals(effectiveDate);

        return this.stream()
                .filter(searchEffectiveDate)
                .findAny();
    }

    public ExchangeRatesArchiveTable searchEffectiveDateByMonth(int year, Month month) {

        boolean leapYear = LocalDate.of(year, 1, 1).isLeapYear();

        return filterByEffectiveDateFrom(LocalDate.of(year, month, 1))
                .filterByEffectiveDateTo(LocalDate.of(year, month, month.length(leapYear)));
    }

    public Optional<DailyExchangeRates> searchTradingDate(LocalDate tradingDate) {

        Predicate<DailyExchangeRates> searchTradingDate = dailyExchangeRates -> dailyExchangeRates.getTradingDate().equals(tradingDate);

        return this.stream()
                .filter(searchTradingDate)
                .findAny();
    }

    public ExchangeRatesArchiveTable searchTradingDateByMonth(int year, Month month) {

        boolean leapYear = LocalDate.of(year, 1, 1).isLeapYear();

        return filterByTradingDateFrom(LocalDate.of(year, month, 1))
                .filterByTradingDateTo(LocalDate.of(year, month, month.length(leapYear)));
    }

    public ExchangeRatesArchiveTable filterByTradingDateFrom(LocalDate after) {
        List<DailyExchangeRates> list = this.stream()
                .filter((dailyTable -> after.compareTo(dailyTable.getTradingDate()) <= 0))
                .collect(Collectors.toList());
        return new ExchangeRatesArchiveTable(list);
    }

    public ExchangeRatesArchiveTable filterByTradingDateTo(LocalDate before) {
        List<DailyExchangeRates> list = this.stream()
                .filter((dailyTable -> before.compareTo(dailyTable.getTradingDate()) >= 0))
                .collect(Collectors.toList());
        return new ExchangeRatesArchiveTable(list);
    }

    public ExchangeRatesArchiveTable filterByEffectiveDateFrom(LocalDate after) {
        List<DailyExchangeRates> list = this.stream()
                .filter((dailyTable -> after.compareTo(dailyTable.getEffectiveDate()) <= 0))
                .collect(Collectors.toList());
        return new ExchangeRatesArchiveTable(list);
    }

    public ExchangeRatesArchiveTable filterByEffectiveDateTo(LocalDate before) {
        List<DailyExchangeRates> list = this.stream()
                .filter((dailyTable -> before.compareTo(dailyTable.getEffectiveDate()) >= 0))
                .collect(Collectors.toList());
        return new ExchangeRatesArchiveTable(list);
    }

    public ExchangeRatesArchiveTable forEachDay(Function<DailyExchangeRates, ExchangeRatesTable> function) {
        ExchangeRatesArchiveTable list = new ExchangeRatesArchiveTable(this);
        for (int i = 0; i < list.size(); i++) {
            DailyExchangeRates dailyExchangeRates = list.get(i).copy();
            //function.apply(dailyExchangeRates);
            dailyExchangeRates.setRates(function.apply(dailyExchangeRates));
            list.set(i, dailyExchangeRates);
        }
        return list;
    }

    public static class ExchangeRatesArchiveTableDeserializer implements JsonDeserializer<ExchangeRatesArchiveTable> {
        public ExchangeRatesArchiveTable deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
            ExchangeRatesArchiveTable list = new ExchangeRatesArchiveTable();
            for (JsonElement item : json.getAsJsonArray()) {
                list.add(context.deserialize(item, DailyExchangeRates.class));
            }
            return list;
        }
    }
}
