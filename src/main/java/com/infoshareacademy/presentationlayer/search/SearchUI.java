package com.infoshareacademy.presentationlayer.search;

import com.infoshareacademy.configuration.PropertiesLoader;
import com.infoshareacademy.domain.DailyExchangeRates;
import com.infoshareacademy.presentationlayer.CollectionView;
import com.infoshareacademy.presentationlayer.Menu;
import com.infoshareacademy.presentationlayer.ValuesScanner;
import com.infoshareacademy.services.DailyExchangeRatesSearchService;
import com.infoshareacademy.services.ExchangeRatesSearchService;
import com.infoshareacademy.services.NBPApiManager;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class SearchUI {

    private static final int MIN_YEAR = 2021;
    private static final int MAX_YEAR = 2023;

    private final DailyExchangeRatesSearchService exchangeRatesSearchService;
    private List<DailyExchangeRates> dailyExchangeRates;
    private final Menu menu;
    private boolean isRunning;

    public SearchUI() {

        exchangeRatesSearchService = NBPApiManager.getInstance().getDailyExchangeSearchRatesService();
        this.menu = new Menu();
        getListOfOptions();

        this.isRunning = true;
    }

    SearchUI(DailyExchangeRatesSearchService exchangeRatesSearchService, Menu menu, boolean isRunning) {
        this.exchangeRatesSearchService = exchangeRatesSearchService;
        this.menu = menu;
        this.isRunning = isRunning;
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
                .setDescription("Back to main menu")
                .setMethod(() -> this.isRunning = false)
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving a table number")
                .setMethod(() -> {
                    String tableNo = ValuesScanner.scanStringWithLength("Enter a table number (example \"004/C/NBP/2022\")");
                    dailyExchangeRates = exchangeRatesSearchService.searchTableNo(tableNo);
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving a full effective date")
                .setMethod(() -> {
                    LocalDate effectiveDate = ValuesScanner.scanLocalDate("Enter a date (example \"" +
                            LocalDate.of(2022, 3, 1).
                                    format(DateTimeFormatter.ofPattern(PropertiesLoader.getInstance().returnDateFormat()))
                            + "\")");
                    exchangeRatesSearchService
                            .searchEffectiveDate(effectiveDate)
                            .ifPresent(CollectionView::displayDailyExchangeRates);
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving a year and a month of effective date")
                .setMethod(() -> {
                    DateRange dateRange = getDateRange();
                    dailyExchangeRates = exchangeRatesSearchService.searchEffectiveDateByTimeRange(dateRange.dateFrom, dateRange.dateTo);
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving a full trading date")
                .setMethod(() -> {
                    LocalDate effectiveDate = ValuesScanner.scanLocalDate("Enter a date (example \"" +
                            LocalDate.of(2022, 3, 1).
                                    format(DateTimeFormatter.ofPattern(PropertiesLoader.getInstance().returnDateFormat()))
                            + "\")");
                    exchangeRatesSearchService
                            .searchTradingDate(effectiveDate)
                            .ifPresent(CollectionView::displayDailyExchangeRates);
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving a year and a month of trading date")
                .setMethod(() -> {
                    DateRange dateRange = getDateRange();
                    dailyExchangeRates = exchangeRatesSearchService.searchTradingDateByTimeRange(dateRange.dateFrom, dateRange.dateTo);
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for rates by giving a currency name")
                .setMethod(() -> {
                    String currency = ValuesScanner.scanStringWithLength("Enter the currency name");
                    dailyExchangeRates = exchangeRatesSearchService.forEachDay(exchangeRates -> exchangeRates.setRates(new ExchangeRatesSearchService(exchangeRates
                            .getRates())
                            .searchCurrency(currency)));
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for rates by giving a currency code")
                .setMethod(() -> {
                    String code = ValuesScanner.scanStringWithLength("Enter the currency code (example \"USD\")");
                    dailyExchangeRates = exchangeRatesSearchService.forEachDay(exchangeRates -> new ExchangeRatesSearchService(exchangeRates
                            .getRates())
                            .searchCode(code)
                            .ifPresent(exchangeRate -> exchangeRates.setRates(List.of(exchangeRate))));
                    displayResult();
                })
        );
    }

    private void displayResult() {
        if (exchangeRatesSearchService.getDailyExchangeRates().isEmpty()) {
            System.out.println("No results.");
        } else {
            CollectionView.displayExchangeRatesArchiveTable(dailyExchangeRates);
        }
    }

    private DateRange getDateRange() {

        int year = ValuesScanner.scanIntegerInRange("Enter a year from " + MIN_YEAR, MIN_YEAR, MAX_YEAR);
        Optional<Month> month = ValuesScanner.scanMonth("Enter a month (examples: 2, february) or '-'");
        LocalDate dateFrom;
        LocalDate dateTo;

        if (month.isPresent()) {
            boolean leapYear = LocalDate.of(year, 1, 1).isLeapYear();
            dateFrom = LocalDate.of(year, month.get(), 1);
            dateTo = LocalDate.of(year, month.get(), month.get().length(leapYear));
        } else {
            dateFrom = LocalDate.of(year, Month.JANUARY, 1);
            dateTo = LocalDate.of(year, Month.DECEMBER, 31);
        }

        return new DateRange(dateFrom, dateTo);
    }

    private static class DateRange {
        LocalDate dateFrom;
        LocalDate dateTo;

        public DateRange(LocalDate dateFrom, LocalDate dateTo) {
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
        }
    }

}