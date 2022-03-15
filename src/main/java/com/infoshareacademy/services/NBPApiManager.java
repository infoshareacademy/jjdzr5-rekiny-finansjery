package com.infoshareacademy.services;

import com.infoshareacademy.api.ApiFromFile;
import com.infoshareacademy.domain.DailyExchangeRates;
import com.infoshareacademy.domain.ExchangeRate;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class NBPApiManager {
    private List<DailyExchangeRates> collectionsOfExchangeRates;

    private static NBPApiManager INSTANCE;

    public synchronized static NBPApiManager getInstance(){
        if(INSTANCE != null){
            return  INSTANCE;
        }
        INSTANCE = new NBPApiManager();
        return INSTANCE;
    }

    private NBPApiManager(){
        collectionsOfExchangeRates = new ApiFromFile().loadDb();
    }

    public List<DailyExchangeRates> getCollectionsOfExchangeRates() {
        return collectionsOfExchangeRates;
    }

    public DailyExchangeRatesFiltrationService getDailyExchangeRatesService() {
        return new DailyExchangeRatesFiltrationService(collectionsOfExchangeRates);
    }

    public DailyExchangeRatesSearchService getDailyExchangeSearchRatesService() {
        return new DailyExchangeRatesSearchService(collectionsOfExchangeRates);
    }

    public Optional<DailyExchangeRates> findDailyTable(String no){
        return collectionsOfExchangeRates.stream().filter(table -> table.getNo().equals(no.toUpperCase())).findFirst();
    }

    public Optional<ExchangeRate> findExchangeRate(String no, String code){
        Optional<DailyExchangeRates> dailyTable = collectionsOfExchangeRates.stream().filter(table -> table.getNo().equals(no.toUpperCase())).findFirst();
        if(dailyTable.isPresent()){
            return dailyTable.get().getRates().stream().filter(table -> table.getCode().equals(code.toUpperCase())).findFirst();
        }
        return Optional.empty();
    }

    public boolean removeDailyTable(String no){
        return collectionsOfExchangeRates.removeIf(table -> table.getNo().equals(no.toUpperCase()));
    }

    public boolean removeExchangeRate(String no, String code){
        Optional<DailyExchangeRates> dailyTable = findDailyTable(no);
        if(dailyTable.isPresent()){
            return dailyTable.get().getRates().removeIf(table -> table.getCode().equals(code.toUpperCase()));
        }
        return false;
    }

    public boolean addDailyTable(DailyExchangeRates table){
        table.setNo(table.getNo().toUpperCase());
        if(findDailyTable(table.getNo()).isPresent()){
            return false;
        }
        return collectionsOfExchangeRates.add(table);
    }

    public boolean addExchangeRate(String no, ExchangeRate exchangeRate){
        exchangeRate.setCode(exchangeRate.getCode().toUpperCase());
        Optional<DailyExchangeRates> dailyTable = findDailyTable(no.toUpperCase());
        if(dailyTable.isPresent()){
            Optional<ExchangeRate> existingExchangeRate = dailyTable.get()
                    .getRates().stream().
                    filter(table -> table.getCode().equals(exchangeRate.getCode())).findFirst();
            if(existingExchangeRate.isPresent()){
                return false;
            }
            return dailyTable.get().getRates().add(exchangeRate);
        }
        return false;
    }

    public boolean saveCollection(){
        return new ApiFromFile().saveDb(collectionsOfExchangeRates);
    }
}
