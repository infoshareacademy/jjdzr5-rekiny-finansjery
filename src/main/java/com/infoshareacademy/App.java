package com.infoshareacademy;

import com.Configuration.PropertiesLoader;
import com.infoshareacademy.presentationlayer.BetterMenu;
import com.infoshareacademy.presentationlayer.ValuesScanner;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class App
{
    public static void main( String[] args ) throws IOException {
        AtomicReference<Boolean> stayInLoop = new AtomicReference<>(true); // co to?

        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadProperties();

 /*       BetterMenu menu = new BetterMenu();

        menu.addMenuOption(new BetterMenu.MenuOption().
                setDescription("Properties Loader").
                setMethod(()->{
                    PropertiesLoader p = new PropertiesLoader();
                    try {
                        p.propertiesLoader();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        }*/




/*        System.out.println( "Rekiny Finansjery" );

        NBPApiManager nbpApiManager = new NBPApiManager();

        UsageExamplesCode.showExamples(nbpApiManager);*/
    }
}
