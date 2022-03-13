package com.infoshareacademy.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infoshareacademy.domain.DailyExchangeRates;

import java.util.ArrayList;
import java.util.List;

public abstract class ApiDataSource {

    public abstract List<DailyExchangeRates> loadDb();

    protected List<DailyExchangeRates> fromJson(String response) {
        Gson gson = ExtendedGson.getExtendedGson();
        return gson.fromJson(response, new TypeToken<ArrayList<DailyExchangeRates>>(){}.getType());
    }

    protected String toJson(List<DailyExchangeRates> dataBase) {
        Gson gson = ExtendedGson.getExtendedGson();
        return gson.toJson(dataBase);
    }
}
