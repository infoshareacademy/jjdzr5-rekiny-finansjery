package com.infoshareacademy.presentationlayer.filtration;

import com.infoshareacademy.services.DailyExchangeRatesService;

import java.util.function.Consumer;
import java.util.function.Function;

class FiltrationOption {
    private String description;
    private Consumer<DailyExchangeRatesService> filter;

    public String getDescription() {
        return description;
    }

    public Consumer<DailyExchangeRatesService> getFilter() {
        return filter;
    }

    public FiltrationOption setDescription(String description) {
        this.description = description;
        return this;
    }
    public FiltrationOption setFilter(Consumer<DailyExchangeRatesService> filter) {
        this.filter = filter;
        return this;
    }
}
