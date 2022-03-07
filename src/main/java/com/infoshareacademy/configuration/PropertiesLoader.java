package com.infoshareacademy.configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class PropertiesLoader {

    public void loadProperties() throws IOException {
        Path path = Paths.get("db.properties");
        try{
            List<String> parameters = Files.readAllLines(path);
            for (String configLine : parameters){
                System.out.println(configLine);
            }
        } catch (IOException e){
            Files.createFile(path);
            Files.writeString(path, "order = ascending\n" +
                    "date-format = dd/mm/yyyy");

            System.out.println("Problem with loading config file. I've created a default one.");
        }

    }

}
