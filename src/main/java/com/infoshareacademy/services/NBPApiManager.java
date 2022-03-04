package com.infoshareacademy.services;

import com.infoshareacademy.api.ApiFromFile;
import com.infoshareacademy.domain.DailyExchangeRates;

import java.util.List;

public class NBPApiManager {
    private List<DailyExchangeRates> collectionsOfExchangeRates;

    public NBPApiManager(){
        collectionsOfExchangeRates = new ApiFromFile().loadDb();
    }

    public List<DailyExchangeRates> getCollectionsOfExchangeRates() {
        return collectionsOfExchangeRates;
    }

    public DailyExchangeRatesFiltrationService getDailyExchangeRatesService() {
        return new DailyExchangeRatesFiltrationService(collectionsOfExchangeRates);
    }

    //

    public boolean saveCollection(){
        return new ApiFromFile().saveDb(collectionsOfExchangeRates);
    }
}
