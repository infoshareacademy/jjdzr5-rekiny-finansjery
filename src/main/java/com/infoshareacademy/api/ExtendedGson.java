package com.infoshareacademy.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.infoshareacademy.api.localDataAdapter.LocalDataDeserializer;
import com.infoshareacademy.api.localDataAdapter.LocalDataSerializer;
import com.infoshareacademy.data.ExchangeRatesArchiveTable;
import com.infoshareacademy.data.ExchangeRatesTable;


import java.time.LocalDate;

public class ExtendedGson {
    public static Gson getExtendedGson(){
        return new GsonBuilder()
                .registerTypeAdapter(ExchangeRatesArchiveTable.class, new ExchangeRatesArchiveTable.ExchangeRatesArchiveTableDeserializer())
                .registerTypeAdapter(ExchangeRatesTable.class, new ExchangeRatesTable.ExchangeRatesTableDeserializer())
                .registerTypeAdapter(LocalDate.class, new LocalDataSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDataDeserializer())
                .create();

    }
}
