package com.infoshareacademy.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class PropertiesLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoader.class);

    private static PropertiesLoader INSTANCE;

    private static Properties properties;

    public synchronized static PropertiesLoader getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        return new PropertiesLoader();
    }

    private PropertiesLoader() {
        Path path = Paths.get("db.properties");
        try {
            loadFromFile(path);
        } catch (IOException e) {
            properties = createDefaultConfiguration();
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

        Path path = Paths.get("db.properties");
        try(FileOutputStream outputStream = new FileOutputStream(path.toString())) {
            defaultProperties.store(outputStream, "default config");
            LOGGER.info("Problem with loading config file. I've created a default one.");
        }
        catch (IOException e){
            LOGGER.error("Problem with loading config file. Couldn't create default config file.");
        }
        return defaultProperties;
    }

    public String getProperty(String property) {
        return properties.getProperty(property);
    }

    public String returnOrder() {
        return properties.getProperty("order");
    }

    public String returnDateFormat() {
        return properties.getProperty("date-format");
    }

}
