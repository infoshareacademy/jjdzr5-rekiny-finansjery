package com.infoshareacademy.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class ExchangeRatesTable extends Vector<ExchangeRate> {
    public ExchangeRatesTable(){}
    public ExchangeRatesTable(List<ExchangeRate> list){
        super(list);
    }

    public ExchangeRatesTable filterBySellPriceFrom(double min){
        List<ExchangeRate> list = this.stream()
                .filter((currency -> min <= currency.getAsk()))
                .collect(Collectors.toList());
        return new ExchangeRatesTable(list);
    }
    public ExchangeRatesTable filterBySellPriceTo(double max){
        List<ExchangeRate> list = this.stream()
                .filter((currency -> max >= currency.getAsk()))
                .collect(Collectors.toList());
        return new ExchangeRatesTable(list);
    }
    public ExchangeRatesTable filterByBuyPriceFrom(double min){
        List<ExchangeRate> list = this.stream()
                .filter((currency -> min <= currency.getBid()))
                .collect(Collectors.toList());
        return new ExchangeRatesTable(list);
    }
    public ExchangeRatesTable filterByBuyPriceTo(double max){
        List<ExchangeRate> list = this.stream()
                .filter((currency -> max >= currency.getBid()))
                .collect(Collectors.toList());
        return new ExchangeRatesTable(list);
    }

    public ExchangeRatesTable filterByShortName(String... selectedCurrencies){
        List<String> selectedCurrenciesList = Arrays.asList(selectedCurrencies);
        List<ExchangeRate> list = this.stream()
                .filter((currency ->
                        selectedCurrenciesList.contains(currency.getCode())
                ))
                .collect(Collectors.toList());
        return new ExchangeRatesTable(list);
    }

    public static class ExchangeRatesTableDeserializer implements JsonDeserializer<ExchangeRatesTable> {
        public ExchangeRatesTable deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
            ExchangeRatesTable list = new ExchangeRatesTable();
            for (JsonElement item : json.getAsJsonArray()) {
                list.add(context.deserialize(item, ExchangeRate.class));
            }
            return list;
        }
    }
}
