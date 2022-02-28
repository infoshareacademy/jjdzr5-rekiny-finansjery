package com.infoshareacademy;

import com.infoshareacademy.presentationlayer.BetterMenu;
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

        BetterMenu menu = new BetterMenu();

        menu.addMenuOption(new BetterMenu.MenuOption().
                setDescription("New Menu Option").
                setMethod(()->{
                    System.out.println("This is a test You've chosen '0'.");
        }));

        menu.addMenuOption(new BetterMenu.MenuOption().
                setDescription("New Menu Option").
                setMethod(()->{
                    System.out.println("This is a test. You've chosen '1'.");
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
