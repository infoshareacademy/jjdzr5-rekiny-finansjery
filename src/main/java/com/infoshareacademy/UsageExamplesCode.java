package com.infoshareacademy;

import com.infoshareacademy.presentationlayer.CollectionView;
import com.infoshareacademy.data.ExchangeRatesArchiveTable;

import java.time.LocalDate;

public class UsageExamplesCode {
     interface Test{
         void method();
     }

     public void showTest(Test test){
         test.method();
     }

    public static void showExamples(NBPApiManager nbpApiManager){

         Runnable runnable = new Runnable() {
             @Override
             public void run() {

             }
         };

         /*
        ExchangeRatesArchiveTable exchangeRatesArchiveTable = nbpApiManager.getCollectionsOfExchangeRates();
        displayExchangeRatesArchiveTable(exchangeRatesArchiveTable);

        DailyExchangeRates daily =  exchangeRatesArchiveTable.get(0);
        daily.setNo("dsdasdsadsadsa");
        nbpApiManager.saveCollection();

        exchangeRatesArchiveTable.remove(exchangeRatesArchiveTable.get(0));
        nbpApiManager.saveCollection();

        Test test = new Test() {
            @Override
            public void method() {

            }
        };






        System.out.println("============================================================================");
        ExchangeRatesArchiveTable fromToExchangeRatesArchiveTable = nbpApiManager
                .getCollectionsOfExchangeRates()
                .filterByEffectiveDateTo(LocalDate.of(2022,1,25))
                .filterByEffectiveDateFrom(LocalDate.of(2022, 1, 21));

        for(DailyExchangeRates dailyExchangeRates : fromToExchangeRatesArchiveTable){
            System.out.println(dailyExchangeRates);
        }

        System.out.println("============================================================================");
        fromToExchangeRatesArchiveTable = nbpApiManager
                .getCollectionsOfExchangeRates()
                .filterByEffectiveDateTo(LocalDate.of(2022,1,24))
                .filterByEffectiveDateFrom(LocalDate.of(2022, 1, 21))
                .forEachDay(dailyExchangeRates -> dailyExchangeRates.getRates().filterByShortName("USD"));


        for(DailyExchangeRates dailyExchangeRates : fromToExchangeRatesArchiveTable){
            System.out.println(dailyExchangeRates);
        }

        System.out.println("============================================================================");
        fromToExchangeRatesArchiveTable = nbpApiManager
                .getCollectionsOfExchangeRates()
                .filterByEffectiveDateTo(LocalDate.of(2022,1,24))
                .filterByEffectiveDateFrom(LocalDate.of(2022, 1, 21))
                .forEachDay(dailyExchangeRates -> dailyExchangeRates.getRates().filterByBuyPriceFrom(1).filterByBuyPriceTo(3));

        for(DailyExchangeRates dailyExchangeRates : fromToExchangeRatesArchiveTable){
            System.out.println(dailyExchangeRates);
        }

        System.out.println("============================================================================");
        fromToExchangeRatesArchiveTable = nbpApiManager
                .getCollectionsOfExchangeRates()
                .filterByEffectiveDateTo(LocalDate.of(2022,1,24))
                .filterByEffectiveDateFrom(LocalDate.of(2022, 1, 21));


        for(DailyExchangeRates dailyExchangeRates : fromToExchangeRatesArchiveTable){
            System.out.println(dailyExchangeRates);
        }

        System.out.println("============================================================================");
        Optional<DailyExchangeRates> lastDay = nbpApiManager
                .getCollectionsOfExchangeRates()
                .getLatestEffectiveDate();

        if(lastDay.isPresent()){
            ExchangeRatesTable lastDayRates = lastDay
                    .get()
                    .getRates()
                    .filterByShortName("CZK", "USD");
            displayExchangeRatesTable(lastDayRates);
        }
=======
        ExchangeRatesArchiveTable exchangeRatesArchiveTable = nbpApiManager.getCollectionsOfExchangeRates(); // pobiera kolekcję
        CollectionView.displayExchangeRatesArchiveTable(exchangeRatesArchiveTable.filterByTradingDateTo(LocalDate.of(2022,01,10))); // wyśwetla zawartość kolekcji

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
>>>>>>> 49f73f92ee0acd48875a40e63a3ad56171a2bd30
*/

    }


}
