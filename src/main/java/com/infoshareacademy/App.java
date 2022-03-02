package com.infoshareacademy;

import com.infoshareacademy.presentationlayer.filtration.FiltrationUI;
import com.infoshareacademy.services.NBPApiManager;
import com.infoshareacademy.presentationlayer.Menu;
import com.infoshareacademy.presentationlayer.ValuesScanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

public class App
{

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

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
                setDescription("New Menu Option").
                setMethod(()->{
                    System.out.println("This is a test. You've chosen '1'.");
        }));

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Exit").
                setMethod(()->{
                    stayInLoop.set(false);
        }));

        LOGGER.error("test");

        while(stayInLoop.get()){
            menu.displayMenu();
            menu.executeSelectedOption(ValuesScanner.scanIntegerInRange("Select the desired option", 0 , menu.getMenuSize()));
        }




/*        System.out.println( "Rekiny Finansjery" );

        NBPApiManager nbpApiManager = new NBPApiManager();

        UsageExamplesCode.showExamples(nbpApiManager);*/
    }
}
