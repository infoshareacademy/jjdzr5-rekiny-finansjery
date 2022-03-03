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
                .setDescription("Search for DailyExchangeRates by giving table number")
                .setMethod(() -> {
                    String tableNo = ValuesScanner.scanString("Enter table number (example \"004\")");
                    Optional<DailyExchangeRates> dailyExchangeRates = nbpApiManager
                            .getDailyExchangeRatesSearchService()
                            .searchTableNo(2022, tableNo);

                    if (dailyExchangeRates.isPresent()) {
                        System.out.println(dailyExchangeRates.get().toString());
                    } else {
                        System.out.println("No results.");
                    }
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for DailyExchangeRates by giving effective date")
                .setMethod(() -> {
                    LocalDate effectiveDate = ValuesScanner.scanLocalDate("Enter date (example \"2022-02-08\")");
                    Optional<DailyExchangeRates> dailyExchangeRates = nbpApiManager
                            .getDailyExchangeRatesSearchService()
                            .searchEffectiveDate(effectiveDate);

                    if (dailyExchangeRates.isPresent()) {
                        System.out.println(dailyExchangeRates.get().toString());
                    } else {
                        System.out.println("No results.");
                    }
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search DailyExchangeRates by giving year of effective date")
                .setMethod(() -> {
                    int year = ValuesScanner.scanIntegerInRange("Enter year (from 2020)", 0, 2023);
                    exchangeRatesSearchService = nbpApiManager
                            .getDailyExchangeRatesSearchService()
                            .searchEffectiveDateByYear(year);

                    if (exchangeRatesSearchService == null) {
                        System.out.println("No results.");
                    } else {
                        CollectionView.displayExchangeRatesArchiveTable(exchangeRatesSearchService.getDailyExchangeRates());
                    }
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search DailyExchangeRates by giving year and month of effective date")
                .setMethod(() -> {
                    int year = ValuesScanner.scanIntegerInRange("Enter year (from 2020)", 0, 2023);
                    Month month = ValuesScanner.scanMonth("Enter month (examples: 2, february, February");
                    exchangeRatesSearchService = nbpApiManager
                            .getDailyExchangeRatesSearchService()
                            .searchEffectiveDateByMonth(year, month);

                    if (exchangeRatesSearchService == null) {
                        System.out.println("No results.");
                    } else {
                        CollectionView.displayExchangeRatesArchiveTable(exchangeRatesSearchService.getDailyExchangeRates());
                    }
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for DailyExchangeRates by giving trading date")
                .setMethod(() -> {
                    LocalDate effectiveDate = ValuesScanner.scanLocalDate("Enter date (example \"2022-02-08\")");
                    Optional<DailyExchangeRates> dailyExchangeRates = nbpApiManager
                            .getDailyExchangeRatesSearchService()
                            .searchTradingDate(effectiveDate);

                    if (dailyExchangeRates.isPresent()) {
                        System.out.println(dailyExchangeRates.get().toString());
                    } else {
                        System.out.println("No results.");
                    }
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search DailyExchangeRates by giving year of trading date")
                .setMethod(() -> {
                    int year = ValuesScanner.scanIntegerInRange("Enter year (from 2020)", 0, 2023);
                    exchangeRatesSearchService = nbpApiManager
                            .getDailyExchangeRatesSearchService()
                            .searchTradingDateByYear(year);

                    if (exchangeRatesSearchService == null) {
                        System.out.println("No results.");
                    } else {
                        CollectionView.displayExchangeRatesArchiveTable(exchangeRatesSearchService.getDailyExchangeRates());
                    }
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search DailyExchangeRates by giving year and month of trading date")
                .setMethod(() -> {
                    int year = ValuesScanner.scanIntegerInRange("Enter year (from 2020)", 0, 2023);
                    Month month = ValuesScanner.scanMonth("Enter month (examples: 2, february, February");
                    exchangeRatesSearchService = nbpApiManager
                            .getDailyExchangeRatesSearchService()
                            .searchTradingDateByMonth(year, month);

                    if (exchangeRatesSearchService == null) {
                        System.out.println("No results.");
                    } else {
                        CollectionView.displayExchangeRatesArchiveTable(exchangeRatesSearchService.getDailyExchangeRates());
                    }
                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Search for ExchangeRates by giving currency name or code")
                .setMethod(() -> {

                })
        );
        menu.addMenuOption(new Menu.MenuOption()
                .setDescription("Back to main menu")
                .setMethod(() -> this.isRunning = false)
        );
    }

}
