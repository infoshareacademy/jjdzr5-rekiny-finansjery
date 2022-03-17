package com.infoshareacademy.presentationlayer.filtration;

import com.infoshareacademy.configuration.PropertiesLoader;
import com.infoshareacademy.services.ExchangeRatesFiltrationService;
import com.infoshareacademy.services.NBPApiManager;
import com.infoshareacademy.services.DailyExchangeRatesFiltrationService;
import com.infoshareacademy.presentationlayer.CollectionView;
import com.infoshareacademy.presentationlayer.SimpleCustomTable;
import com.infoshareacademy.presentationlayer.ValuesScanner;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FiltrationUI {
    DailyExchangeRatesFiltrationService exchangeRatesService;

    public void filtrationMenu(){
        boolean selectingFiltration = true;
        NBPApiManager nbpApiManager = NBPApiManager.getInstance();
        List<FiltrationOption> options = getListOfOptions(nbpApiManager);
        exchangeRatesService = nbpApiManager.getDailyExchangeRatesService();
        while(exchangeRatesService != null) {
            int selectedOption = selectOptions(options);
            System.out.println("Selected option: [" + options.get(selectedOption).getDescription() + "]");
            options.get(selectedOption).getFilter().accept(exchangeRatesService);
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
        menuTable.setTopics("ID", "Menu Options");
        for(int i = 1; i < list.size(); i++){
            menuTable.addRow(String.valueOf(i), list.get(i).getDescription());
        }
        menuTable.addRow(String.valueOf(0), list.get(0).getDescription());
        menuTable.displayTable();
    }

    private List<FiltrationOption> getListOfOptions(NBPApiManager nbpApiManager){
        List<FiltrationOption> list = new ArrayList<>();
        list.add(new FiltrationOption().
                setDescription("Back to main menu").
                setFilter((table) -> {
                    exchangeRatesService = null;
                }));
        list.add(new FiltrationOption().
                setDescription("Display result of filtration").
                setFilter((table) -> {
                    CollectionView.displayExchangeRatesArchiveTable(table.getDailyExchangeRates());
                }));
        list.add(new FiltrationOption().
                setDescription("Reset collection").
                setFilter((table) -> {
                    exchangeRatesService = nbpApiManager.getDailyExchangeRatesService();
                }));
        list.add(new FiltrationOption().
                setDescription("Filter daily tables with trading date since selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.scanLocalDate("Enter the threshold date (example \""+
                            LocalDate.of(2022, 03, 01).
                                    format(DateTimeFormatter.ofPattern(PropertiesLoader.getInstance().returnDateFormat()))
                            +"\")");
                    exchangeRatesService = table.filterByTradingDateFrom(date);
                }));
        list.add(new FiltrationOption().
                setDescription("Filter daily tables with trading date until selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.
                            scanLocalDate("Enter the threshold date (example \""+
                                    LocalDate.of(2022, 03, 01).
                                            format(DateTimeFormatter.ofPattern(PropertiesLoader.getInstance().returnDateFormat()))
                                    +"\")");
                    exchangeRatesService = table.
                            filterByTradingDateTo(date);
                }));
        list.add(new FiltrationOption().
                setDescription("Filter daily tables with effective date since selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.scanLocalDate("Enter the threshold date (example \""+
                            LocalDate.of(2022, 03, 01).
                                    format(DateTimeFormatter.ofPattern(PropertiesLoader.getInstance().returnDateFormat()))
                            +"\")");
                    exchangeRatesService = table.
                            filterByEffectiveDateFrom(date);
                }));
        list.add(new FiltrationOption().
                setDescription("Filter daily tables with effective date until selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.scanLocalDate("Enter the threshold date (example \""+
                            LocalDate.of(2022, 03, 01).
                                    format(DateTimeFormatter.ofPattern(PropertiesLoader.getInstance().returnDateFormat()))
                            +"\")");
                    exchangeRatesService = table.
                            filterByEffectiveDateTo(date);
                }));
        list.add(new FiltrationOption().
                setDescription("Filter currencies with selected short names").
                setFilter((table) -> {
                    List<String> currencies = new ArrayList<>(Arrays.asList(ValuesScanner.scanMultipleStrings("Enter selected currencies (example \"USD,SEK\")")));
                    exchangeRatesService = table.forEachDay(dailyExchangeRates -> new ExchangeRatesFiltrationService(dailyExchangeRates.
                            getRates()).
                            filterByShortName(currencies));
                }));
        list.add(new FiltrationOption().
                setDescription("Filter currencies with ask prices above selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    exchangeRatesService = table.forEachDay(dailyExchangeRates -> new ExchangeRatesFiltrationService(dailyExchangeRates.
                            getRates()).
                            filterBySellPriceFrom(value));
                }));
        list.add(new FiltrationOption().
                setDescription("Filter currencies with ask prices below selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    exchangeRatesService = table.forEachDay(dailyExchangeRates -> new ExchangeRatesFiltrationService(dailyExchangeRates.
                            getRates()).
                            filterBySellPriceTo(value));
                }));
        list.add(new FiltrationOption().
                setDescription("Filter currencies with bid prices above selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    exchangeRatesService = table.forEachDay(dailyExchangeRates -> new ExchangeRatesFiltrationService(dailyExchangeRates.
                            getRates()).
                            filterByBuyPriceFrom(value));
                }));
        list.add(new FiltrationOption().
                setDescription("Filter currencies with bid prices belowe selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    exchangeRatesService = table.forEachDay(dailyExchangeRates -> new ExchangeRatesFiltrationService(dailyExchangeRates.
                            getRates()).
                            filterByBuyPriceTo(value));
                }));
        return list;
    }
}
