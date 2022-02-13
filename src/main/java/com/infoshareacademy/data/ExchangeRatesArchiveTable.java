package com.infoshareacademy.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExchangeRatesArchiveTable extends Vector<DailyExchangeRates> {
    public ExchangeRatesArchiveTable(){}
    public ExchangeRatesArchiveTable(List<DailyExchangeRates> list){
        super(list);
    }
}
