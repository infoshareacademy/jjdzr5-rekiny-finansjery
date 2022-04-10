package com.infoshareacademy.presentationlayer;

import com.infoshareacademy.domain.DailyExchangeRates;
import com.infoshareacademy.presentationlayer.filtration.FiltrationUI;
import com.infoshareacademy.presentationlayer.search.SearchUI;
import com.infoshareacademy.services.DailyExchangeRatesFiltrationService;
import com.infoshareacademy.services.DailyExchangeRatesSearchService;
import com.infoshareacademy.services.ExchangeRatesFiltrationService;
import com.infoshareacademy.services.NBPApiManager;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MenuContents {

    public void loadMenu() {
        NBPApiManager nbpApiManager = NBPApiManager.getInstance();
        AtomicReference<Boolean> stayInLoop = new AtomicReference<>(true);

        Menu menu = new Menu();

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("View all elements").
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
                setDescription("Test search and filtration").
                setMethod(() -> {
                    DailyExchangeRatesSearchService exchangeRatesSearchService = NBPApiManager.getInstance().getDailyExchangeSearchRatesService();
                    List<DailyExchangeRates> list = exchangeRatesSearchService
                            .searchTableNoForFiltration("21").forEachDay(table -> new ExchangeRatesFiltrationService(table.getRates())
                            .filterByShortName(Arrays.asList("USD")))
                            .getDailyExchangeRates();
                    CollectionView.displayExchangeRatesArchiveTable(list);
                }));

        menu.displayMenuWithReturnAndExecute("Exit program",()->{});
    }
}
