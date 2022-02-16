package com.infoshareacademy;

import com.infoshareacademy.PresentationLayer.CollectionView;
import com.infoshareacademy.data.DailyExchangeRates;
import com.infoshareacademy.data.ExchangeRate;
import com.infoshareacademy.data.ExchangeRatesArchiveTable;
import com.infoshareacademy.data.ExchangeRatesTable;

import java.time.LocalDate;
import java.util.Optional;

public class UsageExamplesCode {
    public static void showExamples(NBPApiManager nbpApiManager){


        ExchangeRatesArchiveTable exchangeRatesArchiveTable = nbpApiManager.getCollectionsOfExchangeRates(); // pobiera kolekcję
        CollectionView.displayExchangeRatesArchiveTable(exchangeRatesArchiveTable.filterByEffectiveDateFrom(LocalDate.of(2022,02,10))); // wyśwetla zawartość kolekcji

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
