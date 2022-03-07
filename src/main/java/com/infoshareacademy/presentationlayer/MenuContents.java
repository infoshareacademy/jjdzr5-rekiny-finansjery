package com.infoshareacademy.presentationlayer;

import com.infoshareacademy.configuration.PropertiesLoader;
import com.infoshareacademy.presentationlayer.Menu;


import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class MenuContents {

    public void loadMenu() throws IOException {

        AtomicReference<Boolean> stayInLoop = new AtomicReference<>(true);

        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadProperties(); //todo handle the exception throwing properly (at the moment it's in method signature)

        Menu menu = new Menu();

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Properties Loader").
                setMethod(() -> {
                    PropertiesLoader p = new PropertiesLoader();
                    try {
                        p.loadProperties();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("New Menu Option").
                setMethod(() -> {
                    System.out.println("This is a test. You've chosen '1'.");
                }));

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Exit").
                setMethod(() -> {
                    stayInLoop.set(false);
                }));

        menu.displayMenu();
        while (stayInLoop.get()) {
            menu.executeSelectedOption(ValuesScanner.scanIntegerInRange("Select the desired option", 0, menu.getMenuSize()));
        }
    }
}
