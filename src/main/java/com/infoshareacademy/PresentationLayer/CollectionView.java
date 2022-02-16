package com.infoshareacademy.PresentationLayer;

import com.infoshareacademy.data.DailyExchangeRates;
import com.infoshareacademy.data.ExchangeRate;
import com.infoshareacademy.data.ExchangeRatesArchiveTable;
import com.infoshareacademy.data.ExchangeRatesTable;

public class CollectionView {


    public static void displayExchangeRatesArchiveTable(ExchangeRatesArchiveTable table){
        for(int i=0; i<table.size(); i++){
            displayDailyExchangeRate(table.get(i));
        }
    }
    public static void displayDailyExchangeRate(DailyExchangeRates rate){
        System.out.println(rate.getNo() + " " + rate.getTradingDate() + " " + rate.getEffectiveDate());
        displayExchangeRatesTable(rate.getRates());
    }
    public static void displayExchangeRatesTable(ExchangeRatesTable table){
        for(int j=0; j < table.size(); j++){
            displayExchangeRate(table.get(j));
        }
    }
    public static void displayExchangeRate(ExchangeRate rate) {
        System.out.println(rate.getCode() + " (" +
                rate.getCurrency() + ") | " +
                rate.getBid() + " | " +
                rate.getAsk() + " | ");
    }

}
