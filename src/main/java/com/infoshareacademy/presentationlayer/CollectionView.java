package com.infoshareacademy.presentationlayer;

import com.infoshareacademy.domain.DailyExchangeRates;
import com.infoshareacademy.domain.ExchangeRate;
import com.infoshareacademy.services.DailyExchangeRatesFiltrationService;
import de.vandermeer.asciitable.AsciiTable;

import java.util.List;

public class CollectionView {

//todo: display table parameter using foreach

    public static void displayExchangeRatesArchiveTable(List<DailyExchangeRates> list) {
        List<DailyExchangeRates> tables = list;
        for (DailyExchangeRates table : tables) {
            displayDailyExchangeRates(table);
        }
    }

    public static void displayDailyExchangeRates(DailyExchangeRates table) {
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow(table.getNo(), table.getTradingDate(), table.getEffectiveDate(), "");
        asciiTable.addRule();
        asciiTable.addRow("Code", "Currency Name", "Bid Price", "Asking Price");
        asciiTable.addRule();

        for (ExchangeRate rate : table.getRates()) {
            asciiTable.addRow(rate.getCode(), rate.getCurrency(), rate.getBid(), rate.getAsk());
        }
        asciiTable.addRule();
        String rend = asciiTable.render();
        System.out.println(rend);
    }
}
