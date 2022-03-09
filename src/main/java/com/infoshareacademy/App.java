package com.infoshareacademy;

import com.infoshareacademy.configuration.PropertiesLoader;
import com.infoshareacademy.presentationlayer.Menu;
import com.infoshareacademy.presentationlayer.MenuContents;
import com.infoshareacademy.presentationlayer.filtration.FiltrationUI;
import com.infoshareacademy.services.NBPApiManager;
import com.infoshareacademy.presentationlayer.Menu;
import com.infoshareacademy.presentationlayer.ValuesScanner;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

public class App
{
    public static void main( String[] args ) throws IOException {

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



        System.out.println( "______     _    _              ______ _                       _                 \n" +
                "| ___ \\   | |  (_)             |  ___(_)                     (_)                \n" +
                "| |_/ /___| | ___ _ __  _   _  | |_   _ _ __   __ _ _ __  ___ _  ___ _ __ _   _ \n" +
                "|    // _ \\ |/ / | '_ \\| | | | |  _| | | '_ \\ / _` | '_ \\/ __| |/ _ \\ '__| | | |\n" +
                "| |\\ \\  __/   <| | | | | |_| | | |   | | | | | (_| | | | \\__ \\ |  __/ |  | |_| |\n" +
                "\\_| \\_\\___|_|\\_\\_|_| |_|\\__, | \\_|   |_|_| |_|\\__,_|_| |_|___/ |\\___|_|   \\__, |\n" +
                "                         __/ |                              _/ |           __/ |\n" +
                "                        |___/                              |__/           |___/ ");

        NBPApiManager nbpApiManager = new NBPApiManager();

        MenuContents menuContents = new MenuContents();
        menuContents.loadMenu(nbpApiManager);


    }
}
