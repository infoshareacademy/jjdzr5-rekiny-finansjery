package com.infoshareacademy.configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicReference;

public class PropertiesLoader {

    private static PropertiesLoader INSTANCE;

    private static Properties properties;

    public synchronized static PropertiesLoader getInstance(){
        if(INSTANCE != null){
            return  INSTANCE;
        }
        return new PropertiesLoader();
    }

    private PropertiesLoader(){
        Path path = Paths.get("db.properties");
        try{
            loadFromFile(path);
        }
        catch (IOException e){
            properties = checkIfConfigFileExistsIfNotCreateDefaultOne();
        }
    }

    private void loadFromFile(Path path) throws IOException {
        properties = new Properties();
        properties.load(Files.newBufferedReader(path));
    }

    public Properties checkIfConfigFileExistsIfNotCreateDefaultOne() {

        Path path = Paths.get("db.properties");
        Map<String,String> defaultProperties = new HashMap<>();
        defaultProperties.put("order", "ascending");
        defaultProperties.put("date-format", "dd.MM.yyyy");
        AtomicReference<String> rawProperties = new AtomicReference<>("");
        defaultProperties.forEach((key, value)-> rawProperties.set(rawProperties.get()+key+"="+value+"\n"));
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
                Files.writeString(path, rawProperties.get());

                Properties properties = new Properties();
                properties.load(new ByteArrayInputStream(rawProperties.get().getBytes()));
                System.out.println("Problem with loading config file. I've created a default one.");
                return properties;
            }
        }
        catch (IOException e){
        }
        return null;
    }

    public String getProperty(String property) {
        return properties.getProperty(property);
    }

    public String returnOrder (){
        return properties.getProperty("order");
    }
    public String returnDateFormat (){
        return properties.getProperty("date-format");
    }

}
