package com.infoshareacademy.ui.filtration;

import com.infoshareacademy.NBPApiManager;
import com.infoshareacademy.ui.ValuesScanner;
import com.infoshareacademy.UsageExamplesCode;
import com.infoshareacademy.data.ExchangeRatesArchiveTable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FiltrationUI {

    public void filtrationMenu(NBPApiManager nbpApiManager){
        boolean selectingFiltration = true;
        List<FiltrationDisplayOption> options = getListOfOptions(nbpApiManager);
        ExchangeRatesArchiveTable collection = nbpApiManager.getCollectionsOfExchangeRates();
        while(collection != null) {
            int selectedOption = selectOptions(options);
            System.out.println("Selected option: [" + options.get(selectedOption).getDescription() + "]");
            collection = options.get(selectedOption).getFilter().apply(collection);
        }
    }

    private int selectOptions(List<FiltrationDisplayOption> list){
        int longestLine = list.
                stream().
                max((o1, o2)->((Integer)o1.getDescription().length()).compareTo(o2.getDescription().length())).
                get().
                getDescription().
                length();
        String tableFormat = "| %-3s | %-" + longestLine + "s |%n";
        System.out.format("+-----+-" + "-".repeat(longestLine) + "-+%n");
        System.out.format(tableFormat, "id", "menu options");
        System.out.format("+-----+-" + "-".repeat(longestLine) + "-+%n");
        for(int i = 0; i < list.size(); i++){
            System.out.format(tableFormat, i, list.get(i).getDescription());
        }
        System.out.format("+-----+-" + "-".repeat(longestLine) + "-+%n");
        int selectedOption = -1;
        while(selectedOption < 0 || selectedOption >= list.size()){
            selectedOption = ValuesScanner.scanInteger("Enter options id");
        }
        return selectedOption;
    }

    private List<FiltrationDisplayOption> getListOfOptions(NBPApiManager nbpApiManager){
        List<FiltrationDisplayOption> list = new ArrayList<>();
        list.add(new FiltrationDisplayOption().
                setDescription("display result of filtration").
                setFilter((table) -> {
                    UsageExamplesCode.
                            displayExchangeRatesArchiveTable(table);
                    return table;
                }));
        list.add(new FiltrationDisplayOption().
                setDescription("reset collection").
                setFilter((table) -> {
                    table = nbpApiManager.
                            getCollectionsOfExchangeRates();
                    return table;
                }));
        list.add(new FiltrationDisplayOption().
                setDescription("filter daily tables with trading date since selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.scanLocalDate("Enter the threshold date (example \"2022-02-08\")");
                    table = table.
                            filterByTradingDateFrom(date);
                    return table;
                }));
        list.add(new FiltrationDisplayOption().
                setDescription("filter daily tables with trading date until selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.scanLocalDate("Enter the threshold date (example \"2022-02-08\")");
                    table = table.
                            filterByTradingDateTo(date);
                    return table;
                }));
        list.add(new FiltrationDisplayOption().
                setDescription("filter daily tables with effective date since selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.scanLocalDate("Enter the threshold date (example \"2022-02-08\")");
                    table = table.
                            filterByEffectiveDateFrom(date);
                    return table;
                }));
        list.add(new FiltrationDisplayOption().
                setDescription("filter daily tables with effective date until selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.scanLocalDate("Enter the threshold date (example \"2022-02-08\")");
                    table = table.
                            filterByEffectiveDateTo(date);
                    return table;
                }));
        list.add(new FiltrationDisplayOption().
                setDescription("filter currencies with selected short names").
                setFilter((table) -> {
                    String[] currencies = ValuesScanner.scanMultipleStrings("Enter selected currencies (example \"USD,SEK\")");
                    table = table.forEachDay(dailyExchangeRates -> dailyExchangeRates.
                            getRates().
                            filterByShortName(currencies));
                    return table;
                }));
        list.add(new FiltrationDisplayOption().
                setDescription("filter currencies with ask prices above selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    table = table.forEachDay(dailyExchangeRates -> dailyExchangeRates.
                            getRates().
                            filterBySellPriceFrom(value));
                    return table;
                }));
        list.add(new FiltrationDisplayOption().
                setDescription("filter currencies with ask prices below selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    table = table.forEachDay(dailyExchangeRates -> dailyExchangeRates.
                            getRates().
                            filterBySellPriceTo(value));
                    return table;
                }));
        list.add(new FiltrationDisplayOption().
                setDescription("filter currencies with bid prices above selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    table = table.forEachDay(dailyExchangeRates -> dailyExchangeRates.
                            getRates().
                            filterByBuyPriceFrom(value));
                    return table;
                }));
        list.add(new FiltrationDisplayOption().
                setDescription("filter currencies with bid prices belowe selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    table = table.forEachDay(dailyExchangeRates -> dailyExchangeRates.
                            getRates().
                            filterByBuyPriceTo(value));
                    return table;
                }));
        list.add(new FiltrationDisplayOption().
                setDescription("back to main menu").
                setFilter((table) -> {
                    ExchangeRatesArchiveTable o = null;
                    return o;
                }));
        return list;
    }
}
