package com.infoshareacademy.data;

import java.time.LocalDate;

public class DailyExchangeRates {
    private String table;
    private String no;
    private LocalDate tradingDate;
    private LocalDate effectiveDate;
    private ExchangeRatesTable rates;

    public DailyExchangeRates(String table, String no, LocalDate tradingDate, LocalDate effectiveDate, ExchangeRatesTable rates) {
        this.table = table;
        this.no = no;
        this.tradingDate = tradingDate;
        this.effectiveDate = effectiveDate;
        this.rates = rates;
    }

    public DailyExchangeRates() {}

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public LocalDate getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(LocalDate tradingDate) {
        this.tradingDate = tradingDate;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public ExchangeRatesTable getRates() {
        return rates;
    }

    public void setRates(ExchangeRatesTable rates) {
        this.rates = rates;
    }

    public DailyExchangeRates copy(){
        return new DailyExchangeRates(table, no, tradingDate, effectiveDate, new ExchangeRatesTable(rates));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyExchangeRates that = (DailyExchangeRates) o;

        if (!table.equals(that.table)) return false;
        if (!no.equals(that.no)) return false;
        if (!tradingDate.equals(that.tradingDate)) return false;
        if (!effectiveDate.equals(that.effectiveDate)) return false;
        return rates.equals(that.rates);
    }

    @Override
    public int hashCode() {
        int result = table.hashCode();
        result = 31 * result + no.hashCode();
        result = 31 * result + tradingDate.hashCode();
        result = 31 * result + effectiveDate.hashCode();
        result = 31 * result + rates.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ExchangeRatesTable{" +
                "table='" + table + '\'' +
                ", no='" + no + '\'' +
                ", tradingDate='" + tradingDate + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", rates=" + rates +
                '}';
    }
}
