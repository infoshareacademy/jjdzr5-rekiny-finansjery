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

public class DailyExchangeRatesService {

    private List<DailyExchangeRates> dailyExchangeRates;

    public DailyExchangeRatesService(List<DailyExchangeRates> list){
        dailyExchangeRates = new CopyOnWriteArrayList<>(list);
    }

    public List<DailyExchangeRates> getDailyExchangeRates(){
        return new ArrayList<DailyExchangeRates>(dailyExchangeRates);
    }

    public Optional<DailyExchangeRates> getLatestEffectiveDate(){
        return dailyExchangeRates.stream().max(Comparator.comparing(DailyExchangeRates::getEffectiveDate));
    }

    public DailyExchangeRatesService filterByTradingDateFrom(LocalDate after){
        dailyExchangeRates = dailyExchangeRates.stream()
                .filter((dailyTable -> after.compareTo(dailyTable.getTradingDate()) <= 0))
                .collect(Collectors.toList());
        return this;
    }

    public DailyExchangeRatesService filterByTradingDateTo(LocalDate before){
        dailyExchangeRates = dailyExchangeRates.stream()
                .filter((dailyTable -> before.compareTo(dailyTable.getTradingDate()) >= 0))
                .collect(Collectors.toList());
        return this;
    }

    public DailyExchangeRatesService filterByEffectiveDateFrom(LocalDate after){
        dailyExchangeRates = dailyExchangeRates.stream()
                .filter((dailyTable -> after.compareTo(dailyTable.getEffectiveDate()) <= 0))
                .collect(Collectors.toList());
        return this;
    }

    public DailyExchangeRatesService filterByEffectiveDateTo(LocalDate before){
        dailyExchangeRates = dailyExchangeRates.stream()
                .filter((dailyTable -> before.compareTo(dailyTable.getEffectiveDate()) >= 0))
                .collect(Collectors.toList());
        return this;
    }

    public DailyExchangeRatesService forEachDay(Function<DailyExchangeRates, ExchangeRatesService> function){
        CopyOnWriteArrayList<DailyExchangeRates> list = new CopyOnWriteArrayList<>(dailyExchangeRates);
        for(int i=0; i<list.size(); i++){
            DailyExchangeRates exchangeRates = list.get(i).copy();
            ExchangeRatesService exchangeRatesService = function.apply(exchangeRates);
            exchangeRates.setRates(exchangeRatesService.getExchangeRates());
            list.set(i, exchangeRates);
        }
        dailyExchangeRates = list;
        return this;
    }
}
