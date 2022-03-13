package com.infoshareacademy.presentationlayer;

import com.infoshareacademy.configuration.PropertiesLoader;
import com.infoshareacademy.services.NBPApiManager;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class MenuContents {

    public void loadMenu(NBPApiManager nbpApiManager) {

        AtomicReference<Boolean> stayInLoop = new AtomicReference<>(true);

        Menu menu = new Menu();

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Properties Loader").
                setMethod(() -> {
                    PropertiesLoader p = new PropertiesLoader();
                    try {
                        p.checkIfConfigFileExistsIfNotCreateDefaultOne();
                        p.returnDateFormat();
                        p.returnOrder();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }));

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("View All Elements").
                setMethod(() -> {
                    try {
                        CollectionView.displayExchangeRatesArchiveTable(nbpApiManager.getCollectionsOfExchangeRates());
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
