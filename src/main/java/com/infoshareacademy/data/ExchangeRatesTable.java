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
}
