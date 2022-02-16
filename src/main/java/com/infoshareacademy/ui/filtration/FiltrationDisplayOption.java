package com.infoshareacademy.ui.filtration;

import com.infoshareacademy.data.ExchangeRatesArchiveTable;

import java.util.function.Function;

class FiltrationDisplayOption{
    private String description;
    private Function<ExchangeRatesArchiveTable, ExchangeRatesArchiveTable> filter;

    public String getDescription() {
        return description;
    }

    public Function<ExchangeRatesArchiveTable, ExchangeRatesArchiveTable> getFilter() {
        return filter;
    }

    public FiltrationDisplayOption setDescription(String description) {
        this.description = description;
        return this;
    }
    public FiltrationDisplayOption setFilter(Function<ExchangeRatesArchiveTable, ExchangeRatesArchiveTable> filter) {
        this.filter = filter;
        return this;
    }
}
