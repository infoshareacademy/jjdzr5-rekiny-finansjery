package com.infoshareacademy.presentationlayer;

import com.infoshareacademy.domain.DailyExchangeRates;
import com.infoshareacademy.domain.ExchangeRate;
import com.infoshareacademy.presentationlayer.Menu;
import com.infoshareacademy.presentationlayer.ValuesScanner;
import com.infoshareacademy.services.NBPApiManager;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class EditorUI {
    NBPApiManager nbpApiManager;

    public EditorUI(NBPApiManager nbpApiManager){
        this.nbpApiManager = nbpApiManager;

    }

    public void displayEditorMainMenu(){
        Menu menu = new Menu();
        AtomicBoolean stayInLoop = new AtomicBoolean(true);
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
                    newDailyTable.setTradingDate(ValuesScanner.scanLocalDate("Enter trading date"));
                    newDailyTable.setEffectiveDate(ValuesScanner.scanLocalDate("Enter effective date"));
                    if(nbpApiManager.addDailyTable(newDailyTable)) {
                        nbpApiManager.saveCollection();
                    }
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Remove daily table").
                setMethod(()->{
                    if(nbpApiManager.removeDailyTable(ValuesScanner.scanString("Enter table No."))) {
                        nbpApiManager.saveCollection();
                    }
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Back").
                setMethod(()->{
                    stayInLoop.set(false);
                }));

        while(stayInLoop.get()){
            menu.displayMenu();
            menu.executeSelectedOption(ValuesScanner.scanIntegerInRange("Enter id of option", 0, menu.getMenuSize()));
        }
    }

    public void displayEditorMenuOfDailyTable(DailyExchangeRates dailyExchangeRates){
        Menu menu = new Menu();
        AtomicBoolean stayInLoop = new AtomicBoolean(true);
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
                        nbpApiManager.saveCollection();
                    }
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Remove exchange rate").
                setMethod(()->{
                    if(nbpApiManager.removeExchangeRate(dailyExchangeRates.getNo(), ValuesScanner.scanString("Enter currency code"))){
                        nbpApiManager.saveCollection();
                    }
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Back").
                setMethod(()->{
                    stayInLoop.set(false);
                }));

        while(stayInLoop.get()){
            CollectionView.displayDailyExchangeRates(dailyExchangeRates);
            menu.displayMenu();
            menu.executeSelectedOption(ValuesScanner.scanIntegerInRange("Enter id of option", 0, menu.getMenuSize()));
        }
    }

    public void displayEditorMenuOfExchangeRate(ExchangeRate exchangeRate){
        Menu menu = new Menu();
        AtomicBoolean stayInLoop = new AtomicBoolean(true);
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
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Finish edition").
                setMethod(()->{
                    stayInLoop.set(false);
                }));

        while(stayInLoop.get()){
            CollectionView.displayExchangeRate(exchangeRate);
            menu.displayMenu();
            menu.executeSelectedOption(ValuesScanner.scanIntegerInRange("Enter id of option", 0, menu.getMenuSize()));
        }
    }

    public void displayValuesEditorOfDailyTable(DailyExchangeRates dailyExchangeRates){
        Menu menu = new Menu();
        AtomicBoolean stayInLoop = new AtomicBoolean(true);
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Set new No.").
                setMethod(()->{
                    dailyExchangeRates.setNo(ValuesScanner.scanString("Enter new code"));
                    nbpApiManager.saveCollection();
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Set new trading date").
                setMethod(()->{
                    dailyExchangeRates.setTradingDate(ValuesScanner.scanLocalDate("Enter new trading date"));
                    nbpApiManager.saveCollection();
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Set new effective date").
                setMethod(()->{
                    dailyExchangeRates.setEffectiveDate(ValuesScanner.scanLocalDate("Enter new effective date"));
                    nbpApiManager.saveCollection();
                }));
        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Finish edition").
                setMethod(()->{
                    stayInLoop.set(false);
                }));

        while(stayInLoop.get()){
            CollectionView.displayDailyExchangeRates(dailyExchangeRates);
            menu.displayMenu();
            menu.executeSelectedOption(ValuesScanner.scanIntegerInRange("Enter id of option", 0, menu.getMenuSize()));
        }
    }
}
