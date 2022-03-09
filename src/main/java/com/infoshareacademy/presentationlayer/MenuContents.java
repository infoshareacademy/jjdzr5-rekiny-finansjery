package com.infoshareacademy.presentationlayer;

import com.infoshareacademy.NBPApiManager;
import com.infoshareacademy.configuration.PropertiesLoader;
import com.infoshareacademy.presentationlayer.Menu;


import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class MenuContents {

    public void loadMenu(NBPApiManager nbpApiManager) throws IOException {

        AtomicReference<Boolean> stayInLoop = new AtomicReference<>(true);

        Menu menu = new Menu();

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Properties Loader").
                setMethod(() -> {
                    PropertiesLoader p = new PropertiesLoader();
                    try {
                        p.loadConfigurationFromFileOrCreateDefaultOne();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    p.createConfigurationMap();
                }));

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("View All Elements").
                setMethod(() -> {
                    CollectionView.displayExchangeRatesArchiveTable(nbpApiManager.getCollectionsOfExchangeRates());
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
