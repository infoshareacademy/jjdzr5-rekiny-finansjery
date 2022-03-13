package com.infoshareacademy;

import com.infoshareacademy.presentationlayer.CollectionView;
import com.infoshareacademy.presentationlayer.filtration.FiltrationUI;
import com.infoshareacademy.presentationlayer.EditorUI;
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
        NBPApiManager nbpApiManager = NBPApiManager.getInstance();

        Menu menu = new Menu();

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Filtration").
                setMethod(()->{
                    FiltrationUI filtrationUI = new FiltrationUI();
                    filtrationUI.filtrationMenu();
        }));

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Manage data").
                setMethod(()->{
                    EditorUI editorUI = new EditorUI();
                    editorUI.displayEditorMainMenu();
                }));

        menu.addMenuOption(new Menu.MenuOption().
                setDescription("Display all").
                setMethod(()->{
                    CollectionView.displayExchangeRatesArchiveTable(nbpApiManager.getCollectionsOfExchangeRates());
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
