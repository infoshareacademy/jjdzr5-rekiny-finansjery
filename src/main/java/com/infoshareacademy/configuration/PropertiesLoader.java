package com.infoshareacademy.configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class PropertiesLoader {

    public void checkIfConfigFileExistsIfNotCreateDefaultOne() throws IOException {

        Path path = Paths.get("db.properties");

        if(Files.exists(path)){
        } else {
            Files.createFile(path);
            Files.writeString(path, "order=ascending\n" +
                    "date-format=dd.MM.yyyy");
            System.out.println("Problem with loading config file. I've created a default one.");
        }
    }

    public String returnOrder () throws IOException {
        Properties config = new Properties();
        config.load(new FileInputStream("db.properties"));
        return config.getProperty("order");
    }
    public String returnDateFormat () throws IOException {
        Properties config = new Properties();
        config.load(new FileInputStream("db.properties"));
        return config.getProperty("dateFormat");
    }

}
