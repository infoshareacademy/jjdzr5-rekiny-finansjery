package com.infoshareacademy.api;

import com.infoshareacademy.domain.DailyExchangeRates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiFromNbp extends Json {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiFromNbp.class);

    private final String LAST_67_DAYS_TABLES = "https://api.nbp.pl/api/exchangerates/tables/C/last/67/";

    @Override
    public List<DailyExchangeRates> loadDb() {
        return fromJson(getLAST_67_DAYS_TABLES());
    }

    protected String getLAST_67_DAYS_TABLES(){
        return getJsonFromNbp(LAST_67_DAYS_TABLES);
    }

    private String getJsonFromNbp(String url) {
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

}
