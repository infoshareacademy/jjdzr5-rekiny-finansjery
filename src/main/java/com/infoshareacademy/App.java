package com.infoshareacademy;

import com.infoshareacademy.configuration.PropertiesLoader;
import com.infoshareacademy.presentationlayer.MenuContents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ){
        System.out.println("______     _    _              ______ _                       _                 \n" +
                "| ___ \\   | |  (_)             |  ___(_)                     (_)                \n" +
                "| |_/ /___| | ___ _ __  _   _  | |_   _ _ __   __ _ _ __  ___ _  ___ _ __ _   _ \n" +
                "|    // _ \\ |/ / | '_ \\| | | | |  _| | | '_ \\ / _` | '_ \\/ __| |/ _ \\ '__| | | |\n" +
                "| |\\ \\  __/   <| | | | | |_| | | |   | | | | | (_| | | | \\__ \\ |  __/ |  | |_| |\n" +
                "\\_| \\_\\___|_|\\_\\_|_| |_|\\__, | \\_|   |_|_| |_|\\__,_|_| |_|___/ |\\___|_|   \\__, |\n" +
                "                         __/ |                              _/ |           __/ |\n" +
                "                        |___/                              |__/           |___/ ");
        PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
        MenuContents menuContents = new MenuContents();
        menuContents.loadMenu();
    }
}
