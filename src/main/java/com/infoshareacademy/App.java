package com.infoshareacademy;

import com.infoshareacademy.presentationlayer.filtration.FiltrationUI;
import com.infoshareacademy.presentationlayer.search.SearchUI;
import com.infoshareacademy.services.NBPApiManager;
import com.infoshareacademy.presentationlayer.Menu;
import com.infoshareacademy.presentationlayer.ValuesScanner;

import java.util.concurrent.atomic.AtomicReference;

public class App
{
    public static void main( String[] args )
    {
        AtomicReference<Boolean> stayInLoop = new AtomicReference<>(true);
        NBPApiManager nbpApiManager = new NBPApiManager();

        Menu menu = new Menu();

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Filtration").
                setMethod(()->{
                    FiltrationUI filtrationUI = new FiltrationUI();
                    filtrationUI.filtrationMenu(nbpApiManager);
        }));

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Search").
                setMethod(()->{
                    SearchUI searchUI = new SearchUI(nbpApiManager);
                    searchUI.runMenu();
        }));

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Exit").
                setMethod(()->{
                    stayInLoop.set(false);
        }));

        while(stayInLoop.get()){
            menu.displayMenu();
            menu.executeSelectedOption(ValuesScanner.scanIntegerInRange("Select the desired option", 0 , menu.getMenuSize()));
        }




/*        System.out.println( "Rekiny Finansjery" );

        NBPApiManager nbpApiManager = new NBPApiManager();

        UsageExamplesCode.showExamples(nbpApiManager);*/
    }
}
