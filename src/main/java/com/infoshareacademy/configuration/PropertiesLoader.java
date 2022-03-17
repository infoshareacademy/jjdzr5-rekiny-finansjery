package com.infoshareacademy.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.io.*;

public class PropertiesLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoader.class);

    private static PropertiesLoader INSTANCE;

    private static Properties properties;

    private static final Path path = Paths.get("db.properties");

    public synchronized static PropertiesLoader getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        return new PropertiesLoader();
    }

    private PropertiesLoader() {
        try {
            loadFromFile(path);
        } catch (IOException e) {
            properties = createDefaultConfiguration();
            saveToFile(properties);
        }
    }

    private void loadFromFile(Path path) throws IOException {
        properties = new Properties();
        properties.load(Files.newBufferedReader(path));
    }

    public Properties createDefaultConfiguration() {

        Properties defaultProperties = new Properties();
        defaultProperties.put("order", "ascending");
        defaultProperties.put("date-format", "dd.MM.yyyy");
        defaultProperties.put("last-update", LocalDate.now().toString());
        LOGGER.info("Problem with loading config file. I've created a default one.");
        return defaultProperties;
    }

    private void saveToFile(Properties properties) {
        try(FileOutputStream outputStream = new FileOutputStream(path.toString())) {
            properties.store(outputStream, "default config");
        }
        catch (IOException e){
            LOGGER.error("Couldn't create config file.");
        }
    }

    public String getProperty(String property) {
        String value = properties.getProperty(property);
        if (value == null || value.equals("")) {
            value = addDefaultProperty(property);
        }
        return value;
    }
    public void setProperty(String property, String value){
        properties.put(property,value);
        saveToFile(properties);
    }

    public String returnOrder() {
        return getProperty("order");
    }

    public String returnDateFormat() {
        return getProperty("date-format");
    }

    private String addDefaultProperty(String property){
        Properties temp = createDefaultConfiguration();
        String value = temp.getProperty(property);
        properties.put(property,value);
        saveToFile(properties);
        return value;
    }

}
