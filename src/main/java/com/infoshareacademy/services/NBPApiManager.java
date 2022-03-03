package com.infoshareacademy.services;

import com.infoshareacademy.api.ApiFromNbp;
import com.infoshareacademy.domain.DailyExchangeRates;

import java.util.List;

public class NBPApiManager {
    private List<DailyExchangeRates> collectionsOfExchangeRates;

    public NBPApiManager(){
        collectionsOfExchangeRates = ApiFromNbp.loadDb();
    }

    public List<DailyExchangeRates> getCollectionsOfExchangeRates() {
        return collectionsOfExchangeRates;
    }

    public DailyExchangeRatesFiltrationService getDailyExchangeRatesService() {
        return new DailyExchangeRatesFiltrationService(collectionsOfExchangeRates);
    }

    //

    public boolean saveCollection(){
        return ApiFromNbp.saveDb(collectionsOfExchangeRates);
    }
}
