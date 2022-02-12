package com.infoshareacademy.api;

import java.util.List;

public class ExchangeRatesTable {
    private String table;
    private String no;
    private String tradingDate;
    private String effectiveDate;
    private List<Rate> rates;

    public ExchangeRatesTable(String table, String no, String tradingDate, String effectiveDate, List<Rate> rates) {
        this.table = table;
        this.no = no;
        this.tradingDate = tradingDate;
        this.effectiveDate = effectiveDate;
        this.rates = rates;
    }

    public ExchangeRatesTable() {}

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

    public String getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(String tradingDate) {
        this.tradingDate = tradingDate;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExchangeRatesTable that = (ExchangeRatesTable) o;

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
