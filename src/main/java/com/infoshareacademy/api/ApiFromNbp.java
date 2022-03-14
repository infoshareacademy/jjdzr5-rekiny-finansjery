package com.infoshareacademy.api;

import com.infoshareacademy.domain.DailyExchangeRates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ApiFromNbp extends ApiDataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiFromNbp.class);

    private final String LAST_67_DAYS_TABLES = "https://api.nbp.pl/api/exchangerates/tables/c/last/67/";
    private final String RANGE_OF_DATE = "http://api.nbp.pl/api/exchangerates/tables/c/%1$s/%2$s/";
    private final Integer LIMIT_DAYS = 90;

    @Override
    public List<DailyExchangeRates> loadDb() {
        return fromJson(getLast67DaysTables());
    }

    protected String getLast67DaysTables(){
        return getJsonFromNbp(LAST_67_DAYS_TABLES);
    }
    protected List<DailyExchangeRates> getRangeOfDate(LocalDate startDate, LocalDate endDate){
        List<DailyExchangeRates> result = new ArrayList<>();
        LocalDate tempEndDate = (ChronoUnit.DAYS.between(startDate, endDate) > LIMIT_DAYS? startDate.plusDays(LIMIT_DAYS):endDate);
        do {
            try {
                result.addAll(fromJson(getJsonFromNbp(String.format(RANGE_OF_DATE, startDate, tempEndDate))));
            } catch (NullPointerException e) {
                LOGGER.error(e.getMessage());
            }
            startDate = tempEndDate;
            tempEndDate = (ChronoUnit.DAYS.between(startDate, endDate) > LIMIT_DAYS? startDate.plusDays(LIMIT_DAYS):endDate);
        } while (endDate.isAfter(startDate));
        return result;
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
            LOGGER.error(e.getMessage());
        }
        return body;
    }

}
