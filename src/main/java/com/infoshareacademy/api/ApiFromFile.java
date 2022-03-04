package com.infoshareacademy.api;

import com.infoshareacademy.domain.DailyExchangeRates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ApiFromFile extends Json {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiFromFile.class);

    private final String FILE_NAME = "db_table.json";

    @Override
    public List<DailyExchangeRates> loadDb() {
        return fromJson(loadDbFile(FILE_NAME));
    }

    public boolean saveDb(List<DailyExchangeRates> dailyExchangeRates) {
        return saveDbFile(toJson(dailyExchangeRates), FILE_NAME);
    }

    private boolean saveDbFile (String dataBase, String pathToFile){
        Path path = Paths.get(pathToFile);
        try {
            Files.writeString(path, dataBase);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String loadDbFile(String pathToFile) {
        Path path = Paths.get(pathToFile);
        String dataBase = null;
        try {
            if(Files.exists(path)) {
                dataBase = Files.readString(path);
            } else {
                dataBase = new ApiFromNbp().getLAST_67_DAYS_TABLES();
                saveDbFile(dataBase, pathToFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataBase;
    }
}
