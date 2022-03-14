package com.infoshareacademy.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicReference;

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

        Path path = Paths.get("db.properties");
        Map<String, String> defaultPropertiesMap = new HashMap<>();
        defaultPropertiesMap.put("order", "ascending");
        defaultPropertiesMap.put("date-format", "dd.MM.yyyy");
        AtomicReference<String> rawProperties = new AtomicReference<>("");
        defaultPropertiesMap.forEach((key, value) -> rawProperties.set(rawProperties.get() + key + "=" + value + "\n"));
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
                Files.writeString(path, rawProperties.get());

                defaultProperties.load(new ByteArrayInputStream(rawProperties.get().getBytes()));
                LOGGER.warn("Problem with loading config file: {}. Default configuration is used.", path);
            }
        } catch (IOException e) {
            LOGGER.error("Couldn't load configuration file: ", e);
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
