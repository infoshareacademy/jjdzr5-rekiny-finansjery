package com.infoshareacademy.presentationlayer;

import com.infoshareacademy.data.DailyExchangeRates;
import com.infoshareacademy.data.ExchangeRate;
import com.infoshareacademy.data.ExchangeRatesArchiveTable;
import de.vandermeer.asciitable.AsciiTable;

public class CollectionView {

//todo: display table parameter using foreach

    public static void displayExchangeRatesArchiveTable(ExchangeRatesArchiveTable tables) {

        AsciiTable asciiTable = new de.vandermeer.asciitable.AsciiTable();

        for (DailyExchangeRates table : tables) {
            asciiTable.addRule();
            asciiTable.addRow(table.getNo(), table.getTradingDate(), table.getEffectiveDate(), "");
            asciiTable.addRule();
            asciiTable.addRow("Code", "Currency Name", "Bid Price", "Asking Price");
            asciiTable.addRule();

            for (ExchangeRate rate : table.getRates()) {
                asciiTable.addRow(rate.getCode(), rate.getCurrency(), rate.getBid(), rate.getAsk());
            }
        }
        asciiTable.addRule();

        String rend = asciiTable.render();
        System.out.println(rend);
    }

}
