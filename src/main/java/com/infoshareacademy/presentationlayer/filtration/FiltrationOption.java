package com.infoshareacademy.presentationlayer.filtration;

import com.infoshareacademy.services.DailyExchangeRatesFiltrationService;

import java.util.function.Consumer;

class FiltrationOption {
    private String description;
    private Consumer<DailyExchangeRatesFiltrationService> filter;

    public String getDescription() {
        return description;
    }

    public Consumer<DailyExchangeRatesFiltrationService> getFilter() {
        return filter;
    }

    public FiltrationOption setDescription(String description) {
        this.description = description;
        return this;
    }
    public FiltrationOption setFilter(Consumer<DailyExchangeRatesFiltrationService> filter) {
        this.filter = filter;
        return this;
    }
}
