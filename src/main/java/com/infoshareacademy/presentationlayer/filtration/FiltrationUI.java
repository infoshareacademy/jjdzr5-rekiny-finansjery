package com.infoshareacademy.presentationlayer.filtration;

import com.infoshareacademy.services.ExchangeRatesFiltrationService;
import com.infoshareacademy.services.NBPApiManager;
import com.infoshareacademy.services.DailyExchangeRatesFiltrationService;
import com.infoshareacademy.presentationlayer.CollectionView;
import com.infoshareacademy.presentationlayer.SimpleCustomTable;
import com.infoshareacademy.presentationlayer.ValuesScanner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FiltrationUI {
    DailyExchangeRatesFiltrationService exchangeRatesService;

    public void filtrationMenu(NBPApiManager nbpApiManager){
        List<FiltrationOption> options = getListOfOptions(nbpApiManager);
        exchangeRatesService = nbpApiManager.getDailyExchangeRatesFiltrationService();
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
        menuTable.setTopics("id", "menu options");
        for(int i = 0; i < list.size(); i++){
            menuTable.addRow(String.valueOf(i), list.get(i).getDescription());
        }
        menuTable.displayTable();
    }

    private List<FiltrationOption> getListOfOptions(NBPApiManager nbpApiManager){
        List<FiltrationOption> list = new ArrayList<>();
        list.add(new FiltrationOption().
                setDescription("display result of filtration").
                setFilter((table) -> {
                    CollectionView.displayExchangeRatesArchiveTable(table.getDailyExchangeRates());
                }));
        list.add(new FiltrationOption().
                setDescription("reset collection").
                setFilter((table) -> {
                    exchangeRatesService = nbpApiManager.getDailyExchangeRatesFiltrationService();
                }));
        list.add(new FiltrationOption().
                setDescription("filter daily tables with trading date since selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.scanLocalDate("Enter the threshold date (example \"2022-02-08\")");
                    exchangeRatesService = table.filterByTradingDateFrom(date);
                }));
        list.add(new FiltrationOption().
                setDescription("filter daily tables with trading date until selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.scanLocalDate("Enter the threshold date (example \"2022-02-08\")");
                    exchangeRatesService = table.
                            filterByTradingDateTo(date);
                }));
        list.add(new FiltrationOption().
                setDescription("filter daily tables with effective date since selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.scanLocalDate("Enter the threshold date (example \"2022-02-08\")");
                    exchangeRatesService = table.
                            filterByEffectiveDateFrom(date);
                }));
        list.add(new FiltrationOption().
                setDescription("filter daily tables with effective date until selected date").
                setFilter((table) -> {
                    LocalDate date = ValuesScanner.scanLocalDate("Enter the threshold date (example \"2022-02-08\")");
                    exchangeRatesService = table.
                            filterByEffectiveDateTo(date);
                }));
        list.add(new FiltrationOption().
                setDescription("filter currencies with selected short names").
                setFilter((table) -> {
                    String[] currencies = ValuesScanner.scanMultipleStrings("Enter selected currencies (example \"USD,SEK\")");
                    exchangeRatesService = table.forEachDay(dailyExchangeRates -> new ExchangeRatesFiltrationService(dailyExchangeRates.
                            getRates()).
                            filterByShortName(currencies));
                }));
        list.add(new FiltrationOption().
                setDescription("filter currencies with ask prices above selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    exchangeRatesService = table.forEachDay(dailyExchangeRates -> new ExchangeRatesFiltrationService(dailyExchangeRates.
                            getRates()).
                            filterBySellPriceFrom(value));
                }));
        list.add(new FiltrationOption().
                setDescription("filter currencies with ask prices below selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    exchangeRatesService = table.forEachDay(dailyExchangeRates -> new ExchangeRatesFiltrationService(dailyExchangeRates.
                            getRates()).
                            filterBySellPriceTo(value));
                }));
        list.add(new FiltrationOption().
                setDescription("filter currencies with bid prices above selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    exchangeRatesService = table.forEachDay(dailyExchangeRates -> new ExchangeRatesFiltrationService(dailyExchangeRates.
                            getRates()).
                            filterByBuyPriceFrom(value));
                }));
        list.add(new FiltrationOption().
                setDescription("filter currencies with bid prices belowe selected value").
                setFilter((table) -> {
                    double value = ValuesScanner.scanDouble("Enter the threshold value");
                    exchangeRatesService = table.forEachDay(dailyExchangeRates -> new ExchangeRatesFiltrationService(dailyExchangeRates.
                            getRates()).
                            filterByBuyPriceTo(value));
                }));
        list.add(new FiltrationOption().
                setDescription("back to main menu").
                setFilter((table) -> exchangeRatesService = null));
        return list;
    }
}