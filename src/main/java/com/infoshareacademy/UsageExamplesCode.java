package com.infoshareacademy;

import com.infoshareacademy.presentationlayer.CollectionView;
import com.infoshareacademy.data.ExchangeRatesArchiveTable;
import com.infoshareacademy.presentationlayer.Menu;

import java.time.LocalDate;

public class UsageExamplesCode {
    public static void showExamples(NBPApiManager nbpApiManager){

        Menu menu = new Menu();
        menu.showMenu();


//        ExchangeRatesArchiveTable exchangeRatesArchiveTable = nbpApiManager.getCollectionsOfExchangeRates(); // pobiera kolekcję
//        CollectionView.displayExchangeRatesArchiveTable(exchangeRatesArchiveTable.filterByTradingDateTo(LocalDate.of(2022,01,10))); // wyśwetla zawartość kolekcji

//        System.out.println("============================================================================");
//        ExchangeRatesArchiveTable fromToExchangeRatesArchiveTable = nbpApiManager
//                .getCollectionsOfExchangeRates()
//                .filterByEffectiveDateTo(LocalDate.of(2022,1,25))
//                .filterByEffectiveDateFrom(LocalDate.of(2022, 1, 21));
//
//        for(DailyExchangeRates dailyExchangeRates : fromToExchangeRatesArchiveTable){
//            System.out.println(dailyExchangeRates);
//        }
//
//        System.out.println("============================================================================");
//        fromToExchangeRatesArchiveTable = nbpApiManager
//                .getCollectionsOfExchangeRates()
//                .filterByEffectiveDateTo(LocalDate.of(2022,1,24))
//                .filterByEffectiveDateFrom(LocalDate.of(2022, 1, 21))
//                .forEachDay(dailyExchangeRates -> dailyExchangeRates.getRates().filterByShortName("USD"));
//
//
//        for(DailyExchangeRates dailyExchangeRates : fromToExchangeRatesArchiveTable){
//            System.out.println(dailyExchangeRates);
//        }
//
//        System.out.println("============================================================================");
//        fromToExchangeRatesArchiveTable = nbpApiManager
//                .getCollectionsOfExchangeRates()
//                .filterByEffectiveDateTo(LocalDate.of(2022,1,24))
//                .filterByEffectiveDateFrom(LocalDate.of(2022, 1, 21))
//                .forEachDay(dailyExchangeRates -> dailyExchangeRates.getRates().filterByBuyPriceFrom(1).filterByBuyPriceTo(3));
//
//        for(DailyExchangeRates dailyExchangeRates : fromToExchangeRatesArchiveTable){
//            System.out.println(dailyExchangeRates);
//        }
//
//        System.out.println("============================================================================");
//        fromToExchangeRatesArchiveTable = nbpApiManager
//                .getCollectionsOfExchangeRates()
//                .filterByEffectiveDateTo(LocalDate.of(2022,1,24))
//                .filterByEffectiveDateFrom(LocalDate.of(2022, 1, 21));
//
//
//        for(DailyExchangeRates dailyExchangeRates : fromToExchangeRatesArchiveTable){
//            System.out.println(dailyExchangeRates);
//        }
//
//        System.out.println("============================================================================");
//        Optional<DailyExchangeRates> lastDay = nbpApiManager
//                .getCollectionsOfExchangeRates()
//                .getLatestEffectiveDate();
//
//        if(lastDay.isPresent()){
//            ExchangeRatesTable lastDayRates = lastDay
//                    .get()
//                    .getRates()
//                    .filterByShortName("CZK", "USD");
//            displayExchangeRatesTable(lastDayRates);
//        }


    }


}
