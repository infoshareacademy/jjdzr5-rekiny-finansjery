package com.infoshareacademy.presentationlayer.search;

import com.infoshareacademy.domain.DailyExchangeRates;
import com.infoshareacademy.presentationlayer.CollectionView;
import com.infoshareacademy.presentationlayer.Menu;
import com.infoshareacademy.presentationlayer.ValuesScanner;
import com.infoshareacademy.services.DailyExchangeRatesSearchService;
import com.infoshareacademy.services.ExchangeRatesSearchService;
import com.infoshareacademy.services.NBPApiManager;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class SearchUI {

    private static final int MIN_YEAR = 2021;
    private static final int MAX_YEAR = 2023;

    private final DailyExchangeRatesSearchService exchangeRatesSearchService;
    private List<DailyExchangeRates> dailyExchangeRates;
    private final Menu menu;
    private boolean isRunning;

    public SearchUI(NBPApiManager nbpApiManager) {

        exchangeRatesSearchService = nbpApiManager.getDailyExchangeRatesSearchService();
        this.menu = new Menu();
        getListOfOptions();

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

    private void getListOfOptions() {

        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving a table number")
                .setMethod(() -> {
                    String tableNo = ValuesScanner.scanString("Enter a table number (example \"004/C/NBP/2022\")");
                    dailyExchangeRates = exchangeRatesSearchService.searchTableNo(tableNo);
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving a full effective date")
                .setMethod(() -> {
                    LocalDate effectiveDate = ValuesScanner.scanLocalDate("Enter a date (example \"2022-02-08\")");
                    exchangeRatesSearchService
                            .searchEffectiveDate(effectiveDate)
                            .ifPresent(CollectionView::displayDailyExchangeRates);
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
                    dailyExchangeRates = exchangeRatesSearchService.searchEffectiveDateByTimeRange(dateFrom, dateTo);
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving a full trading date")
                .setMethod(() -> {
                    LocalDate effectiveDate = ValuesScanner.scanLocalDate("Enter a date (example \"2022-02-08\")");
                    exchangeRatesSearchService
                            .searchTradingDate(effectiveDate)
                            .ifPresent(CollectionView::displayDailyExchangeRates);
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
                    dailyExchangeRates = exchangeRatesSearchService.searchTradingDateByTimeRange(dateFrom, dateTo);
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for rates by giving a currency name")
                .setMethod(() -> {
                    String currency = ValuesScanner.scanString("Enter the currency name");
                    dailyExchangeRates = exchangeRatesSearchService.forEachDay(exchangeRates -> exchangeRates.setRates(new ExchangeRatesSearchService(exchangeRates
                            .getRates())
                            .searchCurrency(currency)));
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for rates by giving a currency code")
                .setMethod(() -> {
                    String code = ValuesScanner.scanString("Enter the currency code (example \"USD\")");
                    dailyExchangeRates = exchangeRatesSearchService.forEachDay(exchangeRates -> new ExchangeRatesSearchService(exchangeRates
                            .getRates())
                            .searchCode(code)
                            .ifPresent(exchangeRate -> exchangeRates.setRates(List.of(exchangeRate))));
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
            CollectionView.displayExchangeRatesArchiveTable(dailyExchangeRates);
        }
    }

}