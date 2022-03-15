package com.infoshareacademy.presentationlayer;

import com.infoshareacademy.configuration.PropertiesLoader;
import com.infoshareacademy.domain.DailyExchangeRates;
import com.infoshareacademy.domain.ExchangeRate;
import com.infoshareacademy.services.NBPApiManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class EditorUI {
    NBPApiManager nbpApiManager;

    public EditorUI(){
        this.nbpApiManager = NBPApiManager.getInstance();
    }

    public void displayEditorMainMenu(){
        Menu menu = new Menu();
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Find daily table").
                setMethod(()->{
                    Optional dailyTable = nbpApiManager.findDailyTable(ValuesScanner.scanString("Enter table No."));
                    dailyTable.ifPresentOrElse(table -> this.displayEditorMenuOfDailyTable((DailyExchangeRates)table),
                            ()-> System.out.println("There is no such table."));
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Add daily table").
                setMethod(()->{
                    DailyExchangeRates newDailyTable = new DailyExchangeRates();
                    newDailyTable.setTable("C");
                    newDailyTable.setNo(ValuesScanner.scanString("Enter table No."));
                    newDailyTable.setTradingDate(ValuesScanner.scanLocalDate("Enter trading date(example \""+
                            LocalDate.of(2022, 03, 01).
                                    format(DateTimeFormatter.ofPattern(PropertiesLoader.getInstance().returnDateFormat()))
                            +"\")"));
                    newDailyTable.setEffectiveDate(ValuesScanner.scanLocalDate("Enter effective date (example \""+
                            LocalDate.of(2022, 03, 01).
                                    format(DateTimeFormatter.ofPattern(PropertiesLoader.getInstance().returnDateFormat()))
                            +"\")"));
                    if(nbpApiManager.addDailyTable(newDailyTable)) {
                        System.out.println("Added new daily table.");
                        nbpApiManager.saveCollection();
                    }
                    else{
                        System.out.println("Couldn't add new daily table.");
                    }
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Remove daily table").
                setMethod(()->{
                    if(nbpApiManager.removeDailyTable(ValuesScanner.scanString("Enter table No."))) {
                        System.out.println("Removed daily table.");
                        nbpApiManager.saveCollection();
                    }
                    else{
                        System.out.println("Couldn't remove daily table.");
                    }
                }));
        menu.displayMenuWithReturnAndExecute("Back", ()->{});
    }

    public void displayEditorMenuOfDailyTable(DailyExchangeRates dailyExchangeRates){
        Menu menu = new Menu();
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Edit table").
                setMethod(()->{
                    displayValuesEditorOfDailyTable(dailyExchangeRates);
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Find exchange rate").
                setMethod(()->{
                    Optional dailyTable = nbpApiManager.findExchangeRate(dailyExchangeRates.getNo(), ValuesScanner.scanString("Enter code"));
                    dailyTable.ifPresentOrElse(table -> this.displayEditorMenuOfExchangeRate((ExchangeRate) table),
                            ()-> System.out.println("There is no such currency."));
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Add exchange rate").
                setMethod(()->{
                    ExchangeRate exchangeRate = new ExchangeRate();
                    exchangeRate.setCode(ValuesScanner.scanString("Enter currency code"));
                    exchangeRate.setCurrency(ValuesScanner.scanString("Enter currency name"));
                    exchangeRate.setAsk(ValuesScanner.scanDouble("Enter ask price"));
                    exchangeRate.setBid(ValuesScanner.scanDouble("Enter bid price"));
                    if(nbpApiManager.addExchangeRate(dailyExchangeRates.getNo(), exchangeRate)) {
                        System.out.println("Added new exchange rate.");
                        nbpApiManager.saveCollection();
                    }
                    else{
                        System.out.println("Couldn't add new exchange rate.");
                    }
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Remove exchange rate").
                setMethod(()->{
                    if(nbpApiManager.removeExchangeRate(dailyExchangeRates.getNo(), ValuesScanner.scanString("Enter currency code"))){
                        System.out.println("Removed exchange rate.");
                        nbpApiManager.saveCollection();
                    }
                    else{
                        System.out.println("Couldn't remove exchange rate.");
                    }
                }));
        menu.displayMenuWithReturnAndExecute("Back", ()->CollectionView.displayDailyExchangeRates(dailyExchangeRates));
    }

    public void displayEditorMenuOfExchangeRate(ExchangeRate exchangeRate){
        Menu menu = new Menu();
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Set new code").
                setMethod(()->{
                    exchangeRate.setCode(ValuesScanner.scanString("Enter new code"));
                    nbpApiManager.saveCollection();
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Set new currency name").
                setMethod(()->{
                    exchangeRate.setCurrency(ValuesScanner.scanString("Enter new currency name"));
                    nbpApiManager.saveCollection();
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Set new ask price").
                setMethod(()->{
                    exchangeRate.setAsk(ValuesScanner.scanDouble("Enter new ask price"));
                    nbpApiManager.saveCollection();
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Set new bid price").
                setMethod(()->{
                    exchangeRate.setBid(ValuesScanner.scanDouble("Enter new bid price"));
                    nbpApiManager.saveCollection();
                }));
        menu.displayMenuWithReturnAndExecute("Back", ()->CollectionView.displayExchangeRate(exchangeRate));
    }

    public void displayValuesEditorOfDailyTable(DailyExchangeRates dailyExchangeRates){
        Menu menu = new Menu();
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Set new No.").
                setMethod(()->{
                    dailyExchangeRates.setNo(ValuesScanner.scanString("Enter new code"));
                    nbpApiManager.saveCollection();
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Set new trading date").
                setMethod(()->{
                    dailyExchangeRates.setTradingDate(ValuesScanner.scanLocalDate("Enter new trading date (example \""+
                            LocalDate.of(2022, 03, 01).
                                    format(DateTimeFormatter.ofPattern(PropertiesLoader.getInstance().returnDateFormat()))
                            +"\")"));
                    nbpApiManager.saveCollection();
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Set new effective date").
                setMethod(()->{
                    dailyExchangeRates.setEffectiveDate(ValuesScanner.scanLocalDate("Enter new effective date (example \""+
                            LocalDate.of(2022, 03, 01).
                                    format(DateTimeFormatter.ofPattern(PropertiesLoader.getInstance().returnDateFormat()))
                            +"\")"));
                    nbpApiManager.saveCollection();
                }));
        menu.displayMenuWithReturnAndExecute("Return", ()->CollectionView.displayDailyExchangeRates(dailyExchangeRates));
    }
}
