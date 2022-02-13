package com.infoshareacademy;

import com.infoshareacademy.api.ApiFromNbp;
import com.infoshareacademy.data.ExchangeRatesArchiveTable;

public class NBPApiManager {
    private ExchangeRatesArchiveTable collectionsOfExchangeRates;
    NBPApiManager(){
        collectionsOfExchangeRates = ApiFromNbp.loadDb();
    }

    public ExchangeRatesArchiveTable getCollectionsOfExchangeRates() {
        return collectionsOfExchangeRates;
    }

    public boolean saveCollection(){
        return ApiFromNbp.saveDb(collectionsOfExchangeRates);
    }
}
