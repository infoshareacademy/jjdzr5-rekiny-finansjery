package com.infoshareacademy.services;

import com.infoshareacademy.domain.DailyExchangeRates;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DailyExchangeRatesFiltrationService {

    private List<DailyExchangeRates> dailyExchangeRates;

    public DailyExchangeRatesFiltrationService(List<DailyExchangeRates> list){
        dailyExchangeRates = new CopyOnWriteArrayList<>(list);
    }

    public List<DailyExchangeRates> getDailyExchangeRates(){
        return new ArrayList<>(dailyExchangeRates);
    }

    public Optional<DailyExchangeRates> getLatestEffectiveDate(){
        return dailyExchangeRates.stream().max(Comparator.comparing(DailyExchangeRates::getEffectiveDate));
    }

    public DailyExchangeRatesFiltrationService filterByTradingDateFrom(LocalDate after){
        dailyExchangeRates = dailyExchangeRates.stream()
                .filter((dailyTable -> after.compareTo(dailyTable.getTradingDate()) <= 0))
                .collect(Collectors.toList());
        return this;
    }

    public DailyExchangeRatesFiltrationService filterByTradingDateTo(LocalDate before){
        dailyExchangeRates = dailyExchangeRates.stream()
                .filter((dailyTable -> before.compareTo(dailyTable.getTradingDate()) >= 0))
                .collect(Collectors.toList());
        return this;
    }

    public DailyExchangeRatesFiltrationService filterByEffectiveDateFrom(LocalDate after){
        dailyExchangeRates = dailyExchangeRates.stream()
                .filter((dailyTable -> after.compareTo(dailyTable.getEffectiveDate()) <= 0))
                .collect(Collectors.toList());
        return this;
    }

    public DailyExchangeRatesFiltrationService filterByEffectiveDateTo(LocalDate before){
        dailyExchangeRates = dailyExchangeRates.stream()
                .filter((dailyTable -> before.compareTo(dailyTable.getEffectiveDate()) >= 0))
                .collect(Collectors.toList());
        return this;
    }

    public DailyExchangeRatesFiltrationService forEachDay(Function<DailyExchangeRates, ExchangeRatesFiltrationService> function){
        CopyOnWriteArrayList<DailyExchangeRates> list = new CopyOnWriteArrayList<>(dailyExchangeRates);
        for(int i=0; i<list.size(); i++){
            DailyExchangeRates exchangeRates = list.get(i).copy();
            ExchangeRatesFiltrationService exchangeRatesFiltrationService = function.apply(exchangeRates);
            exchangeRates.setRates(exchangeRatesFiltrationService.getExchangeRates());
            list.set(i, exchangeRates);
        }
        dailyExchangeRates = list.stream()
                .filter(table -> !table.getRates().isEmpty())
                .collect(Collectors.toList());
        return this;
    }
}
