package com.terzulli.weatherapptest.utils;

import java.util.ArrayList;
import java.util.Date;

/**
 * TODO levare / modificare questo commento descrittivo
 * Classe per memorizzare la previsione metereologica giornaliera di una città, incluse le sue
 * previsioni orarie
 */
public class DailyForecast {

    private final Date date;
    private final ArrayList<HourlyForecast> hourlyForecast;
    private final float minTemperature;
    private final float maxTemperature;

    public DailyForecast(Date date, ArrayList<HourlyForecast> hourlyForecast, float minTemperature, float maxTemperature) {
        this.date = date;
        this.hourlyForecast = hourlyForecast;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<HourlyForecast> getHourlyForecast() {
        return hourlyForecast;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }
}
