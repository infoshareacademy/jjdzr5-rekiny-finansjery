package com.infoshareacademy.presentationlayer;

import com.infoshareacademy.configuration.PropertiesLoader;
import com.infoshareacademy.domain.DailyExchangeRates;
import com.infoshareacademy.domain.ExchangeRate;
import com.infoshareacademy.services.DailyExchangeRatesFiltrationService;
import de.vandermeer.asciitable.AsciiTable;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

public class CollectionView {

//todo: display table parameter using foreach

    public static void displayExchangeRatesArchiveTable(List<DailyExchangeRates> list) throws IOException{
        List<DailyExchangeRates> tables = list;
        for (DailyExchangeRates table : tables) {
            displayDailyExchangeRates(table);
        }
    }

    public static void displayDailyExchangeRates(DailyExchangeRates table) throws IOException {
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow(table.getNo(), table.getTradingDate().
                format(DateTimeFormatter.
                        ofPattern(new PropertiesLoader().
                                returnDateFormat())),
                table.getEffectiveDate().
                        format(DateTimeFormatter.
                                ofPattern(new PropertiesLoader().
                                        returnDateFormat())), "");
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
