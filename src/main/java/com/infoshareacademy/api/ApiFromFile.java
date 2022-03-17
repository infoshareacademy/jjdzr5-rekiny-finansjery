package com.infoshareacademy.api;

import com.infoshareacademy.configuration.PropertiesLoader;
import com.infoshareacademy.domain.DailyExchangeRates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ApiFromFile extends ApiDataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiFromFile.class);

    private final String FILE_NAME = "db_table.json";

    @Override
    public List<DailyExchangeRates> loadDb() {
        List<DailyExchangeRates> dailyExchangeRates = fromJson(loadDbFile(FILE_NAME));
        List<DailyExchangeRates> updateDailyExchangeRates = update();
        if (!updateDailyExchangeRates.isEmpty()) {
            for (DailyExchangeRates table: dailyExchangeRates) {
                updateDailyExchangeRates.removeIf(t -> t.getNo().equals(table.getNo()));
            }
            dailyExchangeRates.addAll(updateDailyExchangeRates);
            saveDb(dailyExchangeRates);
        }

        switch (PropertiesLoader.getInstance().getProperty("order")) {
            case "descending":
                return dailyExchangeRates.stream()
                        .sorted((e1,e2) -> e2.getEffectiveDate().compareTo(e1.getEffectiveDate()))
                        .collect(Collectors.toList());
            case "ascending":
            default:
                return dailyExchangeRates.stream()
                        .sorted(Comparator.comparing(DailyExchangeRates::getEffectiveDate))
                        .collect(Collectors.toList());
        }
    }

    public boolean saveDb(List<DailyExchangeRates> dailyExchangeRates) {
        return saveDbFile(toJson(dailyExchangeRates), FILE_NAME);
    }

    private boolean saveDbFile(String dataBase, String pathToFile) {
        Path path = Paths.get(pathToFile);
        try {
            Files.writeString(path, dataBase);
        } catch (IOException e) {
            LOGGER.info(".Couldn't save data base file");
            return false;
        }
        return true;
    }

    private String loadDbFile(String pathToFile) {
        Path path = Paths.get(pathToFile);
        String dataBase = null;
        try {
            if (Files.exists(path)) {
                dataBase = Files.readString(path);
            } else {
                dataBase = new ApiFromNbp().getLast67DaysTables();
                saveDbFile(dataBase, pathToFile);
            }
        } catch (IOException e) {
            LOGGER.info("Problem with loading data base file.");
        }
        return dataBase;
    }

    private List<DailyExchangeRates> update() {
        List<DailyExchangeRates> result = new CopyOnWriteArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate lastUpdate = LocalDate.parse(PropertiesLoader.getInstance().getProperty("last-update"));

        if (today.isAfter(lastUpdate)) {
            result.addAll(new ApiFromNbp().getRangeOfDate(lastUpdate.plusDays(1), today));
            LocalDate newLastUpdate = Collections.max(result, Comparator
                    .comparing(DailyExchangeRates::getEffectiveDate))
                    .getEffectiveDate();
            PropertiesLoader.getInstance().setProperty("last-update",newLastUpdate.toString());
            LOGGER.info("added {} new table", result.size());
        }
        return result;
    }

    private DailyExchangeRates getLastDailyExchangeRates(List<DailyExchangeRates> dailyExchangeRates) {
        return Collections.max(dailyExchangeRates,
                Comparator.comparing(DailyExchangeRates::getEffectiveDate));
    }
}
