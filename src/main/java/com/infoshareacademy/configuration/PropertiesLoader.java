package com.infoshareacademy.configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class PropertiesLoader {

    List<String> parameters = new ArrayList<>();

    public void loadConfigurationFromFileOrCreateDefaultOne() throws IOException {

        Path path = Paths.get("db.properties");

        try {
            parameters = Files.readAllLines(path);
        } catch (IOException e) {
            Files.createFile(path);
            Files.writeString(path, "order=ascending\n" +
                    "date-format=dd/mm/yyyy");

            System.out.println("Problem with loading config file. I've created a default one.");
        }
    }

    public Map<String, String> createConfigurationMap() {

        Map<String, String> configMap = new HashMap<>();

        for (String configLine : parameters) {
            String[] keyAndValue = configLine.split("=");
            configMap.put(keyAndValue[0].trim(), keyAndValue[1].trim());
//            System.out.println(keyAndValue[0] + " " + keyAndValue[1]);
        }

        System.out.println("Configuration map created.");

        return configMap;
    }
}
