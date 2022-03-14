package com.infoshareacademy.api;

import com.infoshareacademy.domain.DailyExchangeRates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ApiFromFile extends ApiDataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiFromFile.class);

    private final String FILE_NAME = "db_table.json";

    @Override
    public List<DailyExchangeRates> loadDb() {
        List<DailyExchangeRates> dailyExchangeRates = fromJson(loadDbFile(FILE_NAME));
        List<DailyExchangeRates> updateDailyExchangeRates = update(getLastDailyExchangeRates(dailyExchangeRates));
        if (updateDailyExchangeRates.size() > 0 ) {
            dailyExchangeRates.addAll(updateDailyExchangeRates);
            saveDb(dailyExchangeRates);
        }
        return dailyExchangeRates;
    }

    public boolean saveDb(List<DailyExchangeRates> dailyExchangeRates) {
        return saveDbFile(toJson(dailyExchangeRates), FILE_NAME);
    }

    private boolean saveDbFile (String dataBase, String pathToFile) {
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
                dataBase = new ApiFromNbp().getLast67DaysTables();
                saveDbFile(dataBase, pathToFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataBase;
    }

    private List<DailyExchangeRates> update(DailyExchangeRates lastDailyExchangeRates) {
        List<DailyExchangeRates> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        if (today.isAfter(lastDailyExchangeRates.getEffectiveDate())) {
            result.addAll(new ApiFromNbp().getRangeOfDate(lastDailyExchangeRates.getEffectiveDate().plusDays(1), today));
            LOGGER.debug(result.size() + " new daailyExchangeRates");
        }
        return result;
    }

    private DailyExchangeRates getLastDailyExchangeRates(List<DailyExchangeRates> dailyExchangeRates) {
        return Collections.max(dailyExchangeRates,
                Comparator.comparing(DailyExchangeRates::getEffectiveDate));
    }
}
