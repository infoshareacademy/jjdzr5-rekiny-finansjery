package com.infoshareacademy.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ApiFromNbp {
    private static final String LAST_67_DAYS_TABLES = "https://api.nbp.pl/api/exchangerates/tables/C/last/67/";
    private static final String FILE_NAME = "db_table.json";

    public static List<ExchangeRatesTable> loadDb() {
        return fromJson(loadDbFile());
    }

    public static boolean saveDb(List<ExchangeRatesTable> exchangeRatesTables) {
        return saveDbFile(toJson(exchangeRatesTables));
    }

    private static boolean saveDbFile (String dataBase){
        Path path = Paths.get(FILE_NAME);
        try {
            Files.writeString(path, dataBase);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static String loadDbFile() {
        Path path = Paths.get(FILE_NAME);
        String dataBase = null;
        try {
            if(Files.exists(path)) {
                dataBase = Files.readString(path);
            } else {
                dataBase = getJsonFromNbp(LAST_67_DAYS_TABLES);
                saveDbFile(dataBase);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataBase;
    }

    private static String getJsonFromNbp(String url) {
        HttpResponse<String> response;
        HttpClient client = HttpClient.newHttpClient();
        String body = null;

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                body = response.body();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    private static List<ExchangeRatesTable> fromJson(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Type exchangeRatesTableListType = new TypeToken<ArrayList<ExchangeRatesTable>>(){}.getType();
        return gson.fromJson(response, exchangeRatesTableListType);
    }

    private static String toJson(List<ExchangeRatesTable> dataBase) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(dataBase);
    }
}
