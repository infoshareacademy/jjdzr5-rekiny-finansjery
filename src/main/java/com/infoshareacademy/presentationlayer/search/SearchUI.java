package com.infoshareacademy.presentationlayer.search;

import com.infoshareacademy.NBPApiManager;
import com.infoshareacademy.data.DailyExchangeRates;
import com.infoshareacademy.data.ExchangeRatesArchiveTable;
import com.infoshareacademy.presentationlayer.BetterMenu;
import com.infoshareacademy.presentationlayer.CollectionView;
import com.infoshareacademy.presentationlayer.ValuesScanner;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

public class SearchUI {

    private final BetterMenu menu;
    private boolean isRunning;

    public SearchUI(NBPApiManager nbpApiManager) {
        this.menu = new BetterMenu();
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

        menu.addMenuOption(new BetterMenu.MenuOption()
                .setDescription("Search for DailyExchangeRates by giving table number")
                .setMethod(() -> {
                    String tableNo = ValuesScanner.scanString("Enter table number (example \"004\")");
                    Optional<DailyExchangeRates> dailyExchangeRates = nbpApiManager
                            .getCollectionsOfExchangeRates()
                            .searchTableNo(2022, tableNo);

                    if (dailyExchangeRates.isPresent()) {
                        System.out.println(dailyExchangeRates.get().toString());
                    } else {
                        System.out.println("No results.");
                    }
                })
        );
        menu.addMenuOption(new BetterMenu.MenuOption()
                .setDescription("Search for DailyExchangeRates by giving effective date")
                .setMethod(() -> {
                    LocalDate effectiveDate = ValuesScanner.scanLocalDate("Enter date (example \"2022-02-08\")");
                    Optional<DailyExchangeRates> dailyExchangeRates = nbpApiManager
                            .getCollectionsOfExchangeRates()
                            .searchEffectiveDate(effectiveDate);

                    if (dailyExchangeRates.isPresent()) {
                        System.out.println(dailyExchangeRates.get().toString());
                    } else {
                        System.out.println("No results.");
                    }
                })
        );
        menu.addMenuOption(new BetterMenu.MenuOption()
                .setDescription("Search DailyExchangeRates by giving year of effective date")
                .setMethod(() -> {
                    int year = ValuesScanner.scanIntegerInRange("Enter year (from 2020)", 0, 2023);
                    ExchangeRatesArchiveTable table = nbpApiManager
                            .getCollectionsOfExchangeRates()
                            .searchEffectiveDateByYear(year);

                    if (table.isEmpty()) {
                        System.out.println("No results.");
                    } else {
                        CollectionView.displayExchangeRatesArchiveTable(table);
                    }
                })
        );
        menu.addMenuOption(new BetterMenu.MenuOption()
                .setDescription("Search DailyExchangeRates by giving year and month of effective date")
                .setMethod(() -> {
                    int year = ValuesScanner.scanIntegerInRange("Enter year (from 2020)", 0, 2023);
                    Month month = ValuesScanner.scanMonth("Enter month (examples: 2, february, February");
                    ExchangeRatesArchiveTable table = nbpApiManager
                            .getCollectionsOfExchangeRates()
                            .searchEffectiveDateByMonth(year, month);

                    if (table.isEmpty()) {
                        System.out.println("No results.");
                    } else {
                        CollectionView.displayExchangeRatesArchiveTable(table);
                    }
                })
        );
        menu.addMenuOption(new BetterMenu.MenuOption()
                .setDescription("Search for DailyExchangeRates by giving trading date")
                .setMethod(() -> {
                    LocalDate effectiveDate = ValuesScanner.scanLocalDate("Enter date (example \"2022-02-08\")");
                    Optional<DailyExchangeRates> dailyExchangeRates = nbpApiManager
                            .getCollectionsOfExchangeRates()
                            .searchTradingDate(effectiveDate);

                    if (dailyExchangeRates.isPresent()) {
                        System.out.println(dailyExchangeRates.get().toString());
                    } else {
                        System.out.println("No results.");
                    }
                })
        );
        menu.addMenuOption(new BetterMenu.MenuOption()
                .setDescription("Search DailyExchangeRates by giving year of trading date")
                .setMethod(() -> {
                    int year = ValuesScanner.scanIntegerInRange("Enter year (from 2020)", 0, 2023);
                    ExchangeRatesArchiveTable table = nbpApiManager
                            .getCollectionsOfExchangeRates()
                            .searchTradingDateByYear(year);

                    if (table.isEmpty()) {
                        System.out.println("No results.");
                    } else {
                        CollectionView.displayExchangeRatesArchiveTable(table);
                    }
                })
        );
        menu.addMenuOption(new BetterMenu.MenuOption()
                .setDescription("Search DailyExchangeRates by giving year and month of trading date")
                .setMethod(() -> {
                    int year = ValuesScanner.scanIntegerInRange("Enter year (from 2020)", 0, 2023);
                    Month month = ValuesScanner.scanMonth("Enter month (examples: 2, february, February");
                    ExchangeRatesArchiveTable table = nbpApiManager
                            .getCollectionsOfExchangeRates()
                            .searchTradingDateByMonth(year, month);

                    if (table.isEmpty()) {
                        System.out.println("No results.");
                    } else {
                        CollectionView.displayExchangeRatesArchiveTable(table);
                    }
                })
        );
        menu.addMenuOption(new BetterMenu.MenuOption()
                .setDescription("Search for ExchangeRates by giving currency name or code")
                .setMethod(() -> {

                })
        );
        menu.addMenuOption(new BetterMenu.MenuOption()
                .setDescription("Back to main menu")
                .setMethod(() -> this.isRunning = false)
        );
    }

}
