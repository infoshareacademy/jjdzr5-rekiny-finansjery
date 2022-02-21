package com.infoshareacademy;

import com.infoshareacademy.data.DailyExchangeRates;
import com.infoshareacademy.data.ExchangeRate;
import com.infoshareacademy.data.ExchangeRatesArchiveTable;
import com.infoshareacademy.data.ExchangeRatesTable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class UsageExamplesCode {
    public static void showExamples(NBPApiManager nbpApiManager) {


        ExchangeRatesArchiveTable exchangeRatesArchiveTable = nbpApiManager.getCollectionsOfExchangeRates();
        displayExchangeRatesArchiveTable(exchangeRatesArchiveTable);

        System.out.println("============================================================================");
        ExchangeRatesArchiveTable fromToExchangeRatesArchiveTable = nbpApiManager
                .getCollectionsOfExchangeRates()
                .filterByEffectiveDateTo(LocalDate.of(2022, 1, 25))
                .filterByEffectiveDateFrom(LocalDate.of(2022, 1, 21));

        for (DailyExchangeRates dailyExchangeRates : fromToExchangeRatesArchiveTable) {
            System.out.println(dailyExchangeRates);
        }

        System.out.println("============================================================================");
        fromToExchangeRatesArchiveTable = nbpApiManager
                .getCollectionsOfExchangeRates()
                .filterByEffectiveDateTo(LocalDate.of(2022, 1, 24))
                .filterByEffectiveDateFrom(LocalDate.of(2022, 1, 21))
                .forEachDay(dailyExchangeRates -> dailyExchangeRates.getRates().filterByShortName("USD"));


        for (DailyExchangeRates dailyExchangeRates : fromToExchangeRatesArchiveTable) {
            System.out.println(dailyExchangeRates);
        }

        System.out.println("============================================================================");
        fromToExchangeRatesArchiveTable = nbpApiManager
                .getCollectionsOfExchangeRates()
                .filterByEffectiveDateTo(LocalDate.of(2022, 1, 24))
                .filterByEffectiveDateFrom(LocalDate.of(2022, 1, 21))
                .forEachDay(dailyExchangeRates -> dailyExchangeRates.getRates().filterByBuyPriceFrom(1).filterByBuyPriceTo(3));

        for (DailyExchangeRates dailyExchangeRates : fromToExchangeRatesArchiveTable) {
            System.out.println(dailyExchangeRates);
        }

        System.out.println("============================================================================");
        fromToExchangeRatesArchiveTable = nbpApiManager
                .getCollectionsOfExchangeRates()
                .filterByEffectiveDateTo(LocalDate.of(2022, 1, 24))
                .filterByEffectiveDateFrom(LocalDate.of(2022, 1, 21));


        for (DailyExchangeRates dailyExchangeRates : fromToExchangeRatesArchiveTable) {
            System.out.println(dailyExchangeRates);
        }

        System.out.println("============================================================================");
        Optional<DailyExchangeRates> lastDay = nbpApiManager
                .getCollectionsOfExchangeRates()
                .getLatestEffectiveDate();

        if (lastDay.isPresent()) {
            ExchangeRatesTable lastDayRates = lastDay
                    .get()
                    .getRates()
                    .filterByShortName("CZK", "USD");
            displayExchangeRatesTable(lastDayRates);
        }

        System.out.println("============================================================================");

        Scanner scanner = new Scanner(System.in);
        String searchedPhrase;

        System.out.println("Szukanie po numerze tabeli:");
        while (true) {

            searchedPhrase = scanner.nextLine();

            if (searchedPhrase.equals("q")) {
                break;
            }

            if (searchedPhrase.length() < 3) {
                System.out.println("Searched phrase is too short.");
                continue;
            }

            Optional<DailyExchangeRates> dailyExchangeRates = nbpApiManager
                    .getCollectionsOfExchangeRates()
                    .searchTableNo(2022, searchedPhrase);

            if (dailyExchangeRates.isPresent()) {
                displayDailyExchangeRate(dailyExchangeRates.get());
            } else {
                System.out.println("No results.");
            }

        }

    }

    public static void displayExchangeRatesArchiveTable(ExchangeRatesArchiveTable table) {
        for (int i = 0; i < table.size(); i++) {
            displayDailyExchangeRate(table.get(i));
        }
    }

    public static void displayDailyExchangeRate(DailyExchangeRates rate) {
        System.out.println(rate.getNo() + "\nEffective Date: " + rate.getEffectiveDate() + "\nTrading date: " + rate.getTradingDate());
        displayExchangeRatesTable(rate.getRates());
    }

    public static void displayExchangeRatesTable(ExchangeRatesTable table) {
        for (int j = 0; j < table.size(); j++) {
            displayExchangeRate(table.get(j));
        }
    }

    public static void displayExchangeRate(ExchangeRate rate) {
        System.out.println(rate.getCurrency() + " " + rate.getCode() + " " +
                rate.getBid() + " " +
                rate.getAsk());
    }

}
