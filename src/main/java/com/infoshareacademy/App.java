package com.infoshareacademy;

import com.infoshareacademy.configuration.PropertiesLoader;
import com.infoshareacademy.presentationlayer.Menu;
import com.infoshareacademy.presentationlayer.ValuesScanner;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class App
{
    public static void main( String[] args ) throws IOException {
        AtomicReference<Boolean> stayInLoop = new AtomicReference<>(true); // co to?

        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadProperties();

        Menu menu = new Menu();

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Properties Loader").
                setMethod(()->{
                    PropertiesLoader p = new PropertiesLoader();
                    try {
                        p.loadProperties();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

        menu.displayMenu();
        while(stayInLoop.get()){
            menu.executeSelectedOption(ValuesScanner.scanIntegerInRange("Select the desired option", 0 , menu.getMenuSize()));
        }




        System.out.println( "Rekiny Finansjery" );

        NBPApiManager nbpApiManager = new NBPApiManager();

        UsageExamplesCode.showExamples(nbpApiManager);
    }
}
