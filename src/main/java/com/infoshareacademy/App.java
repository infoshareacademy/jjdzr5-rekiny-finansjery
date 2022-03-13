package com.infoshareacademy;

import com.infoshareacademy.presentationlayer.MenuContents;
import com.infoshareacademy.services.NBPApiManager;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    public static void main(String[] args) throws IOException {

        final Logger LOGGER = LoggerFactory.getLogger(App.class);

            System.out.println("______     _    _              ______ _                       _                 \n" +
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
