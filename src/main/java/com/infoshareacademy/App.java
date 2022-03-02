package com.infoshareacademy;

import com.infoshareacademy.presentationlayer.BetterMenu;
import com.infoshareacademy.presentationlayer.ValuesScanner;
import com.infoshareacademy.presentationlayer.filtration.FiltrationUI;
import com.infoshareacademy.presentationlayer.search.SearchUI;

import java.util.concurrent.atomic.AtomicReference;

public class App
{
    public static void main( String[] args )
    {
        NBPApiManager nbpApiManager = new NBPApiManager();
        AtomicReference<Boolean> stayInLoop = new AtomicReference<>(true);

        BetterMenu menu = new BetterMenu();

        menu.addMenuOption(new BetterMenu.MenuOption().
                setDescription("Search Option").
                setMethod(()->{
                    System.out.println("This is a test You've chosen '0'.");
                    SearchUI searchUI = new SearchUI(nbpApiManager);
                    searchUI.runMenu();
        }));

        menu.addMenuOption(new BetterMenu.MenuOption().
                setDescription("Filtration Option").
                setMethod(()->{
                    System.out.println("This is a test. You've chosen '1'.");
                    FiltrationUI filtrationUI = new FiltrationUI();
                    filtrationUI.filtrationMenu(nbpApiManager);
        }));

        menu.addMenuOption(new BetterMenu.MenuOption().
                setDescription("Exit").
                setMethod(()->{
                    stayInLoop.set(false);
        }));

        menu.displayMenu();
        while(stayInLoop.get()){
            menu.executeSelectedOption(ValuesScanner.scanIntegerInRange("Select the desired option", 0 , menu.getMenuSize()));
        }




/*        System.out.println( "Rekiny Finansjery" );

        NBPApiManager nbpApiManager = new NBPApiManager();

        UsageExamplesCode.showExamples(nbpApiManager);*/
    }
}
