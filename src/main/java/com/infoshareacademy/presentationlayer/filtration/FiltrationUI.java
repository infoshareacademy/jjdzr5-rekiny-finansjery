package com.infoshareacademy.presentationlayer.filtration;

import com.infoshareacademy.NBPApiManager;
import com.infoshareacademy.data.ExchangeRatesArchiveTable;
import com.infoshareacademy.presentationlayer.CollectionView;
import com.infoshareacademy.presentationlayer.SimpleCustomTable;
import com.infoshareacademy.presentationlayer.ValuesScanner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FiltrationUI {

    public void filtrationMenu(NBPApiManager nbpApiManager){
        boolean selectingFiltration = true;
        List<FiltrationOption> options = getListOfOptions(nbpApiManager);
        ExchangeRatesArchiveTable collection = nbpApiManager.getCollectionsOfExchangeRates();
        while(collection != null) {
            int selectedOption = selectOptions(options);
            System.out.println("Selected option: [" + options.get(selectedOption).getDescription() + "]");
            collection = options.get(selectedOption).getFilter().apply(collection);
        }
    }

    private int selectOptions(List<FiltrationOption> list){
        displayOptionsTable(list);

        int selectedOption = -1;
        selectedOption = ValuesScanner.scanIntegerInRange("Enter options id", 0, list.size());
        return selectedOption;
    }

    private void displayOptionsTable(List<FiltrationOption> list){
        SimpleCustomTable menuTable = new SimpleCustomTable(2);
        menuTable.setTopics("id", "menu options");
        for(int i = 0; i < list.size(); i++){
            menuTable.addRow(String.valueOf(i), list.get(i).getDescription());
        }
        menuTable.displayMenu();
    }

    private List<FiltrationOption> getListOfOptions(NBPApiManager nbpApiManager){
        List<FiltrationOption> list = new ArrayList<>();
        list.add(new FiltrationOption().
                setDescription("display result of filtration").
                setFilter((table) -> {
                    CollectionView.displayExchangeRatesArchiveTable(table);
                    return table;
                }));
        list.add(new FiltrationOption().
                setDescription("reset collection").
                setFilter((table) -> {
                    table = nbpApiManager.
                            getCollectionsOfExchangeRates();
                    return table;
                }));
        list.add(new FiltrationOption().
                setDescription("filter daily tables with trading date since selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.scanLocalDate("Enter the threshold date (example \"2022-02-08\")");
                    table = table.
                            filterByTradingDateFrom(date);
                    return table;
                }));
        list.add(new FiltrationOption().
                setDescription("filter daily tables with trading date until selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.scanLocalDate("Enter the threshold date (example \"2022-02-08\")");
                    table = table.
                            filterByTradingDateTo(date);
                    return table;
                }));
        list.add(new FiltrationOption().
                setDescription("filter daily tables with effective date since selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.scanLocalDate("Enter the threshold date (example \"2022-02-08\")");
                    table = table.
                            filterByEffectiveDateFrom(date);
                    return table;
                }));
        list.add(new FiltrationOption().
                setDescription("filter daily tables with effective date until selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.scanLocalDate("Enter the threshold date (example \"2022-02-08\")");
                    table = table.
                            filterByEffectiveDateTo(date);
                    return table;
                }));
        list.add(new FiltrationOption().
                setDescription("filter currencies with selected short names").
                setFilter((table) -> {
                    String[] currencies = ValuesScanner.scanMultipleStrings("Enter selected currencies (example \"USD,SEK\")");
                    table = table.forEachDay(dailyExchangeRates -> dailyExchangeRates.
                            getRates().
                            filterByShortName(currencies));
                    return table;
                }));
        list.add(new FiltrationOption().
                setDescription("filter currencies with ask prices above selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    table = table.forEachDay(dailyExchangeRates -> dailyExchangeRates.
                            getRates().
                            filterBySellPriceFrom(value));
                    return table;
                }));
        list.add(new FiltrationOption().
                setDescription("filter currencies with ask prices below selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    table = table.forEachDay(dailyExchangeRates -> dailyExchangeRates.
                            getRates().
                            filterBySellPriceTo(value));
                    return table;
                }));
        list.add(new FiltrationOption().
                setDescription("filter currencies with bid prices above selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    table = table.forEachDay(dailyExchangeRates -> dailyExchangeRates.
                            getRates().
                            filterByBuyPriceFrom(value));
                    return table;
                }));
        list.add(new FiltrationOption().
                setDescription("filter currencies with bid prices belowe selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    table = table.forEachDay(dailyExchangeRates -> dailyExchangeRates.
                            getRates().
                            filterByBuyPriceTo(value));
                    return table;
                }));
        list.add(new FiltrationOption().
                setDescription("back to main menu").
                setFilter((table) -> {
                    ExchangeRatesArchiveTable o = null;
                    return o;
                }));
        return list;
    }
}
