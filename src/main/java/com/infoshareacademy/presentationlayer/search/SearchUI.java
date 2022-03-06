package com.infoshareacademy.presentationlayer.search;

import com.infoshareacademy.presentationlayer.CollectionView;
import com.infoshareacademy.presentationlayer.Menu;
import com.infoshareacademy.presentationlayer.ValuesScanner;
import com.infoshareacademy.services.DailyExchangeRatesSearchService;
import com.infoshareacademy.services.ExchangeRatesSearchService;
import com.infoshareacademy.services.NBPApiManager;

import java.time.LocalDate;
import java.time.Month;

public class SearchUI {

    private static final int MIN_YEAR = 2021;
    private static final int MAX_YEAR = 2023;

    private DailyExchangeRatesSearchService exchangeRatesSearchService;
    private final Menu menu;
    private boolean isRunning;

    public SearchUI(NBPApiManager nbpApiManager) {

        exchangeRatesSearchService = nbpApiManager.getDailyExchangeRatesSearchService();
        this.menu = new Menu();
        getListOfOptions(nbpApiManager);

        this.isRunning = true;
    }

    public void runMenu() {
        while (isRunning) {
            menu.displayMenu();
            int selectedOption = ValuesScanner.scanIntegerInRange("Enter option", 0, menu.getMenuSize());
            System.out.println("Selected option: [" + selectedOption + "]");
            menu.executeSelectedOption(selectedOption);
        }
    }

    private void getListOfOptions(NBPApiManager nbpApiManager) {

        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Display result")
                .setMethod(this::displayResult)
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Reset collection")
                .setMethod(() -> this.exchangeRatesSearchService = nbpApiManager.getDailyExchangeRatesSearchService())
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving a table number")
                .setMethod(() -> {
                    String tableNo = ValuesScanner.scanString("Enter a table number (example \"004/C/NBP/2022\")");
                    this.exchangeRatesSearchService.searchTableNo(tableNo);
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving a full effective date")
                .setMethod(() -> {
                    LocalDate effectiveDate = ValuesScanner.scanLocalDate("Enter a date (example \"2022-02-08\")");
                    this.exchangeRatesSearchService.searchEffectiveDate(effectiveDate);
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving a year and a month of effective date")
                .setMethod(() -> {
                    int year = ValuesScanner.scanIntegerInRange("Enter a year from " + MIN_YEAR, MIN_YEAR, MAX_YEAR);
                    Month month = ValuesScanner.scanMonth("Enter a month (examples: 2, february) or '-'");
                    LocalDate dateFrom;
                    LocalDate dateTo;

                    if (month == null) {
                        dateFrom = LocalDate.of(year, Month.JANUARY, 1);
                        dateTo = LocalDate.of(year, Month.DECEMBER, 31);
                    } else {
                        boolean leapYear = LocalDate.of(year, 1, 1).isLeapYear();
                        dateFrom = LocalDate.of(year, month, 1);
                        dateTo = LocalDate.of(year, month, month.length(leapYear));
                    }
                    this.exchangeRatesSearchService.searchEffectiveDateByTimeRange(dateFrom, dateTo);
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving a full trading date")
                .setMethod(() -> {
                    LocalDate effectiveDate = ValuesScanner.scanLocalDate("Enter a date (example \"2022-02-08\")");
                    this.exchangeRatesSearchService.searchTradingDate(effectiveDate);
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving a year and a month of trading date")
                .setMethod(() -> {
                    int year = ValuesScanner.scanIntegerInRange("Enter a year from " + MIN_YEAR, MIN_YEAR, MAX_YEAR);
                    Month month = ValuesScanner.scanMonth("Enter a month (examples: 2, february) or '-'");
                    LocalDate dateFrom;
                    LocalDate dateTo;

                    if (month == null) {
                        dateFrom = LocalDate.of(year, Month.JANUARY, 1);
                        dateTo = LocalDate.of(year, Month.DECEMBER, 31);
                    } else {
                        boolean leapYear = LocalDate.of(year, 1, 1).isLeapYear();
                        dateFrom = LocalDate.of(year, month, 1);
                        dateTo = LocalDate.of(year, month, month.length(leapYear));
                    }
                    this.exchangeRatesSearchService.searchTradingDateByTimeRange(dateFrom, dateTo);
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for rates by giving a currency name")
                .setMethod(() -> {
                    String currency = ValuesScanner.scanString("Enter the currency name");
                    this.exchangeRatesSearchService.forEachDay(dailyExchangeRates -> new ExchangeRatesSearchService(dailyExchangeRates
                            .getRates())
                            .searchCurrency(currency));
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for rates by giving a currency code")
                .setMethod(() -> {
                    String code = ValuesScanner.scanString("Enter the currency code (example \"USD\")");
                    this.exchangeRatesSearchService.forEachDay(dailyExchangeRates -> new ExchangeRatesSearchService(dailyExchangeRates
                            .getRates())
                            .searchCode(code));
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Back to main menu")
                .setMethod(() -> this.isRunning = false)
        );
    }

    private void displayResult() {
        if (exchangeRatesSearchService.getDailyExchangeRates().isEmpty()) {
            System.out.println("No results.");
        } else {
            CollectionView.displayExchangeRatesArchiveTable(exchangeRatesSearchService.getDailyExchangeRates());
        }
    }

}