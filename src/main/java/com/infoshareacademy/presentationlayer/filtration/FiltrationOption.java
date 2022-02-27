package com.infoshareacademy.presentationlayer.filtration;

import com.infoshareacademy.data.ExchangeRatesArchiveTable;

import java.util.function.Function;

class FiltrationOption {
    private String description;
    private Function<ExchangeRatesArchiveTable, ExchangeRatesArchiveTable> filter;

    public String getDescription() {
        return description;
    }

    public Function<ExchangeRatesArchiveTable, ExchangeRatesArchiveTable> getFilter() {
        return filter;
    }

    public FiltrationOption setDescription(String description) {
        this.description = description;
        return this;
    }
    public FiltrationOption setFilter(Function<ExchangeRatesArchiveTable, ExchangeRatesArchiveTable> filter) {
        this.filter = filter;
        return this;
    }
}
