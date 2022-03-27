package com.infoshareacademy.presentationlayer;

import com.infoshareacademy.configuration.PropertiesLoader;
import com.infoshareacademy.domain.DailyExchangeRates;
import com.infoshareacademy.domain.ExchangeRate;
import de.vandermeer.asciitable.AsciiTable;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionView {

//todo: display table parameter using foreach

    public static void displayExchangeRatesArchiveTable(List<DailyExchangeRates> list) {
        for (DailyExchangeRates table : sort(list)) {
            displayDailyExchangeRates(table);
        }
    }

    public static void displayDailyExchangeRates(DailyExchangeRates table) {
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow(table.getNo(), table.getTradingDate()
                        .format(DateTimeFormatter
                        .ofPattern(PropertiesLoader
                        .getInstance()
                        .returnDateFormat())),
                        table.getEffectiveDate().
                        format(DateTimeFormatter
                        .ofPattern(PropertiesLoader
                        .getInstance()
                        .returnDateFormat()))
                        , "");
        asciiTable.addRule();
        if (!table.getRates().isEmpty()) {
            asciiTable.addRow("Code", "Currency Name", "Bid Price", "Asking Price");
            asciiTable.addRule();
            for (ExchangeRate rate : table.getRates()) {
                asciiTable.addRow(rate.getCode(), rate.getCurrency(), rate.getBid(), rate.getAsk());
            }
            asciiTable.addRule();
        }
        System.out.println(asciiTable.render());
    }

    public static void displayExchangeRate(ExchangeRate rate) {
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Code", "Currency Name", "Bid Price", "Asking Price");
        asciiTable.addRule();
        asciiTable.addRow(rate.getCode(), rate.getCurrency(), rate.getBid(), rate.getAsk());
        asciiTable.addRule();
        System.out.println(asciiTable.render());
    }

    private static List<DailyExchangeRates> sort(List<DailyExchangeRates> list) {
        Comparator<DailyExchangeRates> dailyExchangeRatesComparator;
        switch (PropertiesLoader.getInstance().getProperty("order")) {
            case "descending":
                dailyExchangeRatesComparator = (o1, o2) -> o2.getEffectiveDate().compareTo(o1.getEffectiveDate());
                break;
            case "ascending":
            default:
                dailyExchangeRatesComparator = Comparator.comparing(DailyExchangeRates::getEffectiveDate);
        }
        return list.stream()
                .sorted(dailyExchangeRatesComparator)
                .collect(Collectors.toList());
    }

}
