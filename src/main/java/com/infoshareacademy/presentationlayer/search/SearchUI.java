package com.infoshareacademy.presentationlayer.search;

import com.infoshareacademy.domain.DailyExchangeRates;
import com.infoshareacademy.presentationlayer.CollectionView;
import com.infoshareacademy.presentationlayer.Menu;
import com.infoshareacademy.presentationlayer.ValuesScanner;
import com.infoshareacademy.services.DailyExchangeRatesSearchService;
import com.infoshareacademy.services.NBPApiManager;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

public class SearchUI {

    private static final int MIN_YEAR = 2021;
    private static final int MAX_YEAR = 2023;

    private DailyExchangeRatesSearchService exchangeRatesSearchService;
    private final Menu menu;
    private boolean isRunning;

    public SearchUI(NBPApiManager nbpApiManager) {
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
                .setDescription("Search for tables by giving table number")
                .setMethod(() -> {
                    String tableNo = ValuesScanner.scanString("Enter table number (example \"004/C/NBP/2022\")");
                    exchangeRatesSearchService = nbpApiManager
                            .getDailyExchangeRatesSearchService()
                            .searchTableNo(tableNo);
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for table by giving effective date")
                .setMethod(() -> {
                    LocalDate effectiveDate = ValuesScanner.scanLocalDate("Enter date (example \"2022-02-08\")");
                    Optional<DailyExchangeRates> dailyExchangeRates = nbpApiManager
                            .getDailyExchangeRatesSearchService()
                            .searchEffectiveDate(effectiveDate);
                    dailyExchangeRates.ifPresent(CollectionView::displayDailyExchangeRates);
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving year of effective date")
                .setMethod(() -> {
                    int year = ValuesScanner.scanIntegerInRange("Enter year (from " + MIN_YEAR + ")", MIN_YEAR, MAX_YEAR);
                    exchangeRatesSearchService = nbpApiManager
                            .getDailyExchangeRatesSearchService()
                            .searchEffectiveDateByTimeRange(LocalDate.of(year, Month.JANUARY, 1), LocalDate.of(year, Month.DECEMBER, 31));
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving year and month of effective date")
                .setMethod(() -> {
                    int year = ValuesScanner.scanIntegerInRange("Enter year (from " + MIN_YEAR + ")", MIN_YEAR, MAX_YEAR);
                    Month month = ValuesScanner.scanMonth("Enter month (examples: 2, february, February");
                    boolean leapYear = LocalDate.of(year, 1, 1).isLeapYear();

                    exchangeRatesSearchService = nbpApiManager
                            .getDailyExchangeRatesSearchService()
                            .searchEffectiveDateByTimeRange(LocalDate.of(year, month, 1), LocalDate.of(year, month, month.length(leapYear)));
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for table by giving trading date")
                .setMethod(() -> {
                    LocalDate effectiveDate = ValuesScanner.scanLocalDate("Enter date (example \"2022-02-08\")");
                    Optional<DailyExchangeRates> dailyExchangeRates = nbpApiManager
                            .getDailyExchangeRatesSearchService()
                            .searchTradingDate(effectiveDate);
                    dailyExchangeRates.ifPresent(CollectionView::displayDailyExchangeRates);
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving year of trading date")
                .setMethod(() -> {
                    int year = ValuesScanner.scanIntegerInRange("Enter year (from " + MIN_YEAR + ")", MIN_YEAR, MAX_YEAR);
                    exchangeRatesSearchService = nbpApiManager
                            .getDailyExchangeRatesSearchService()
                            .searchTradingDateByTimeRange(LocalDate.of(year, Month.JANUARY, 1), LocalDate.of(year, Month.DECEMBER, 31));
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for tables by giving year and month of trading date")
                .setMethod(() -> {

                    int year = ValuesScanner.scanIntegerInRange("Enter year (from " + MIN_YEAR + ")", MIN_YEAR, MAX_YEAR);
                    Month month = ValuesScanner.scanMonth("Enter month (examples: 2, february, February");
                    boolean leapYear = LocalDate.of(year, 1, 1).isLeapYear();

                    exchangeRatesSearchService = nbpApiManager
                            .getDailyExchangeRatesSearchService()
                            .searchTradingDateByTimeRange(LocalDate.of(year, month, 1), LocalDate.of(year, month, month.length(leapYear)));
                    displayResult();
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for rates by giving currency name or code")
                .setMethod(() -> {

                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Back to main menu")
                .setMethod(() -> this.isRunning = false)
        );
    }

    private void displayResult() {
        if (exchangeRatesSearchService == null) {
            System.out.println("No results.");
        } else {
            CollectionView.displayExchangeRatesArchiveTable(exchangeRatesSearchService.getDailyExchangeRates());
        }
    }

}