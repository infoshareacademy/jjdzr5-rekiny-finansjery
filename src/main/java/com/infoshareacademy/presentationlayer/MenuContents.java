package com.infoshareacademy.presentationlayer;

import com.infoshareacademy.presentationlayer.filtration.FiltrationUI;
import com.infoshareacademy.presentationlayer.search.SearchUI;
import com.infoshareacademy.services.NBPApiManager;

import java.util.concurrent.atomic.AtomicReference;

public class MenuContents {

    public void loadMenu() {
        NBPApiManager nbpApiManager = NBPApiManager.getInstance();
        AtomicReference<Boolean> stayInLoop = new AtomicReference<>(true);

        Menu menu = new Menu();

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("View All Elements").
                setMethod(() -> CollectionView.displayExchangeRatesArchiveTable(nbpApiManager.getCollectionsOfExchangeRates())));

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Filtration").
                setMethod(() -> {
                    FiltrationUI filtrationUI = new FiltrationUI();
                    filtrationUI.filtrationMenu();
                }));

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Search").
                setMethod(() -> {
                    SearchUI searchUI = new SearchUI();
                    searchUI.runMenu();
                }));

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Manage data").
                setMethod(() -> {
                    EditorUI editorUI = new EditorUI();
                    editorUI.displayEditorMainMenu();
                }));

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Exit").
                setMethod(() -> stayInLoop.set(false)));

        menu.displayMenu();
        while (stayInLoop.get()) {
            menu.executeSelectedOption(ValuesScanner.scanIntegerInRange("Select the desired option", 0, menu.getMenuSize()));
        }
    }
}
